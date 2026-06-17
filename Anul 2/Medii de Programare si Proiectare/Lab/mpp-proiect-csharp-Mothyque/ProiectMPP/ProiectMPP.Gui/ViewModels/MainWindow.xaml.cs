using ProiectMPP.Client.ViewModels;
using ProiectMPP.Common.Domain;
using ProiectMPP.Common.dto;
using ProiectMPP.Common.Networking;
using ProiectMPP.Common.Utils;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Windows;
using System.Xml.Linq;

namespace ProiectMPP.Client.ViewModels
{
    public partial class MainWindow : MessageController, IObserver
    {
        private IServices server;
        private User currentUser;
        private App mainApp;

        public ObservableCollection<Match> MatchesModel { get; set; } = new ObservableCollection<Match>();
        public ObservableCollection<ClientTicketDTO> TicketsModel { get; set; } = new ObservableCollection<ClientTicketDTO>();

        public MainWindow()
        {
            InitializeComponent();
            dgMatches.ItemsSource = MatchesModel;
            dgTickets.ItemsSource = TicketsModel;
        }

        public void SetServices(IServices server, User user, App mainApp)
        {
            this.server = server;
            this.currentUser = user;
            this.mainApp = mainApp;
            InitData();
        }

        private void InitData()
        {
            try
            {
                MatchesModel.Clear();
                Match[] allMatches = server.GetAllMatches();
                foreach (var m in allMatches)
                {
                    MatchesModel.Add(m);
                }
                txtTicket.Text = "1";
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage("Error loading matches: " + rpcEx.Status.Detail);
            }
            catch (Exception e)
            {
                ShowErrorMessage("Error loading matches: " + e.Message);
            }
        }

        private void onSearch_Click(object sender, RoutedEventArgs e)
        {
            string name = txtName.Text;
            string address = txtAddress.Text;

            if (string.IsNullOrEmpty(name) && string.IsNullOrEmpty(address))
            {
                TicketsModel.Clear();
                return;
            }

            try
            {
                IList<ClientTicketDTO> results = server.FilterTickets(name, address);
                TicketsModel.Clear();
                foreach (var t in results)
                {
                    TicketsModel.Add(t);
                }
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage("Error filtering tickets: " + rpcEx.Status.Detail);
            }
            catch (Exception ex)
            {
                ShowErrorMessage("Error filtering tickets: " + ex.Message);
            }
        }

        private void onUpdate_Click(object sender, RoutedEventArgs e)
        {
            var selected = dgTickets.SelectedItem as ClientTicketDTO;
            if (selected == null)
            {
                ShowErrorMessage("No ticket selected for update.");
                return;
            }

            try
            {
                int newNumber = int.Parse(txtTicket.Text);
                if (newNumber <= 0)
                {
                    ShowErrorMessage("Number of tickets must be positive.");
                    return;
                }

                int oldNumber = selected.NumberOfTickets;
                if (newNumber == oldNumber)
                {
                    ShowInfoMessage("No change in number of tickets.");
                    return;
                }

                Match matchToUpdate = new Match { Id = selected.Match.Id };
                ProiectMPP.Common.Domain.Client clientToUpdate = new ProiectMPP.Common.Domain.Client { Id = selected.Client.Id };

                server.UpdateTickets(matchToUpdate, clientToUpdate, oldNumber, newNumber);

                ShowInfoMessage("Ticket update request sent successfully.");
                txtTicket.Text = "1";
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage("Error updating tickets: " + rpcEx.Status.Detail);
            }
            catch (FormatException)
            {
                ShowErrorMessage("Invalid number of tickets: " + txtTicket.Text);
            }
            catch (Exception ex)
            {
                ShowErrorMessage("Error updating tickets: " + ex.Message);
            }
        }

        private void onBuy_Click(object sender, RoutedEventArgs e)
        {
            string name = txtName.Text;
            string address = txtAddress.Text;
            var selectedMatch = dgMatches.SelectedItem as Match;

            if (selectedMatch == null)
            {
                ShowErrorMessage("No match selected.");
                return;
            }
            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(address))
            {
                ShowErrorMessage("Name and address cannot be empty.");
                return;
            }

            try
            {
                int numberOfTickets = int.Parse(txtTicket.Text);
                if (numberOfTickets <= 0)
                {
                    ShowErrorMessage("Number of tickets must be positive.");
                    return;
                }
                if (selectedMatch.AvailableSeats < numberOfTickets)
                {
                    ShowErrorMessage("Not enough available seats for the selected match.");
                    return;
                }

                server.BuyTickets(selectedMatch, name, address, numberOfTickets);
                ShowInfoMessage("Purchase request sent successfully.");
                txtTicket.Text = "1";
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage("Error purchasing tickets: " + rpcEx.Status.Detail);
            }
            catch (FormatException)
            {
                ShowErrorMessage("Invalid number of tickets: " + txtTicket.Text);
            }
            catch (Exception ex)
            {
                ShowErrorMessage("Error purchasing tickets: " + ex.Message);
            }
        }

        private void onLogout_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                server.Logout(currentUser, this);

                LoginWindow loginWin = new LoginWindow();
                loginWin.SetServer(server);
                loginWin.SetMainApp(mainApp);
                loginWin.Show();

                this.Close();
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage("Error during logout: " + rpcEx.Status.Detail);
            }
            catch (Exception ex)
            {
                ShowErrorMessage("Error during logout: " + ex.Message);
            }
        }

        public void Update()
        {
            Application.Current.Dispatcher.BeginInvoke(() =>
            {
                try
                {
                    InitData();
                    if (!string.IsNullOrWhiteSpace(txtName.Text) || !string.IsNullOrWhiteSpace(txtAddress.Text))
                    {
                        onSearch_Click(null, null);
                    }
                    else
                    {
                        TicketsModel.Clear();
                    }
                }
                catch (Grpc.Core.RpcException rpcEx)
                {
                    ShowErrorMessage("Error refreshing data: " + rpcEx.Status.Detail);
                }
                catch (Exception ex)
                {
                    ShowErrorMessage("Error refreshing data: " + ex.Message);
                }
            });
        }
    }
}
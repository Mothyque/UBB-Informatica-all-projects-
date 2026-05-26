using ProiectMPP.Client.ViewModels;
using ProiectMPP.Common.Domain;
using ProiectMPP.Common.dto;
using ProiectMPP.Common.Networking;
using System;
using System.Windows;

namespace ProiectMPP.Client.ViewModels
{
    public partial class LoginWindow : MessageController
    {
        private IServices server;
        private App mainApp;

        public LoginWindow()
        {
            InitializeComponent();
        }

        public void SetServer(IServices server)
        {
            this.server = server;
        }

        public void SetMainApp(App mainApp)
        {
            this.mainApp = mainApp;
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            string username = txtUsername.Text;
            string password = txtPassword.Password;

            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                ShowErrorMessage("Please enter both username and password.");
                return;
            }

            User user = new User(username, password);

            try
            {
                MainWindow mainWin = new MainWindow();

                server.Login(user, mainWin);

                mainWin.SetServices(server, user, mainApp);
                mainApp.SetCurrentUser(user);

                mainWin.Show();
                this.Close();
            }
            catch (Grpc.Core.RpcException rpcEx)
            {
                ShowErrorMessage($"Login failed: {rpcEx.Status.Detail}");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occurred during login: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
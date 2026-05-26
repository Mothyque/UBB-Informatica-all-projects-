using ProiectMPP.Client.ViewModels;
using ProiectMPP.Common.Domain;
using ProiectMPP.Common.Networking;
using System.Windows;
using System;
using System.Configuration;
using ProiectMPP.Client.Rest;

namespace ProiectMPP.Client
{
    public partial class App : Application
    {
        private IServices server;
        private User currentUser;

        public void SetCurrentUser(User user)
        {
            this.currentUser = user;
        }

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            if (e.Args.Length > 0 && string.Equals(e.Args[0], "rest", StringComparison.OrdinalIgnoreCase))
            {
                Task.Run(async () => {
                    try
                    {
                        await CSharpTest.RunTests();

                        string output = CSharpTest.GetTestOutput();
                        Dispatcher.Invoke(() => 
                        {
                            MessageBox.Show(output, "REST Tests Completed Successfully!", MessageBoxButton.OK, MessageBoxImage.Information, MessageBoxResult.OK, MessageBoxOptions.DefaultDesktopOnly);
                        });
                    }
                    catch (Exception ex)
                    {
                        string output = CSharpTest.GetTestOutput();
                        string fullMessage = $"Test Error:\n{ex.Message}\n\nCaptured Output:\n{output}";
                        Dispatcher.Invoke(() => MessageBox.Show(fullMessage, "Test Error", MessageBoxButton.OK, MessageBoxImage.Error, MessageBoxResult.OK, MessageBoxOptions.DefaultDesktopOnly));
                    }
                    finally
                    {
                        Environment.Exit(0);
                    }
                });
                return;
            }

            try
            {
                string host = ConfigurationManager.AppSettings["ServerHost"] ?? "localhost";
                int port = int.TryParse(ConfigurationManager.AppSettings["ServerPort"], out int parsedPort) ? parsedPort : 55555;
                //server = new ServicesRpcProxy(host, port);
                server = new GrpcServicesProxy(host, port);

                LoginWindow loginWin = new LoginWindow();
                loginWin.SetServer(server);
                loginWin.SetMainApp(this);

                loginWin.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"EROARE FATALĂ:\n{ex.Message}\n\n{ex.StackTrace}");
                Environment.Exit(1);
            }
        }

        protected override void OnExit(ExitEventArgs e)
        {
            if (server != null && currentUser != null)
            {
                try
                {
                    server.Logout(currentUser, null);
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Logout error: " + ex.Message);
                }
            }
            base.OnExit(e);
        }
    }
}
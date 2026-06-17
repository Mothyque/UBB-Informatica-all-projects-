using System.Windows;

namespace ProiectMPP.Client.ViewModels
{
    public abstract class MessageController : Window
    {
        protected void ShowErrorMessage(string message)
        {
            MessageBox.Show(message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }

        protected void ShowInfoMessage(string message)
        {
            MessageBox.Show(message, "Information", MessageBoxButton.OK, MessageBoxImage.Information);
        }
    }
}
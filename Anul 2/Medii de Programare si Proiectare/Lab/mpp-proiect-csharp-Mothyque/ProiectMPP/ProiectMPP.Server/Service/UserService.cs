using ProiectMPP.Common.Domain;
using ProiectMPP.Server.Repository;
using System;
using System.Security.Cryptography;
using System.Text;

namespace ProiectMPP.Server.Service
{
    public class UserService : Service<int, User>
    {
        public UserService(IUserRepository repository) : base(repository)
        {
        }

        public User FindByUsername(string username)
        {
            return ((IUserRepository)repository).FindByUsername(username);
        }

        public User Authenticate(string username, string password)
        {
            User user = FindByUsername(username);
            if (user != null && user.Password.Equals(HashPassword(password)))
            {
                return user;
            }
            return null;
        }

        public string HashPassword(string password)
        {
            if (string.IsNullOrEmpty(password)) return string.Empty;

            using (SHA1 sha1 = SHA1.Create())
            {
                byte[] inputBytes = Encoding.UTF8.GetBytes(password);
                byte[] hashBytes = sha1.ComputeHash(inputBytes);
                StringBuilder sb = new StringBuilder();
                foreach (byte b in hashBytes)
                {
                    sb.Append(b.ToString("x2"));
                }
                return sb.ToString();
            }
        }
    }
}
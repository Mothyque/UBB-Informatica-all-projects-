using ProiectMPP.Common.dto;
using System;
using System.IO;
using System.Text;
using System.Text.Json;

namespace ProiectMPP.Common.Networking
{
    public class NetworkJsonSerializer
    {
        private readonly Stream stream;
        private readonly UTF8Encoding encoding = new UTF8Encoding(false);

        public NetworkJsonSerializer(Stream stream)
        {
            this.stream = stream ?? throw new ArgumentNullException(nameof(stream));
        }

        public void SerializeRequest(Request request)
        {
            if (request == null)
                throw new ArgumentNullException(nameof(request));

            string json = JsonSerializerHelper.SerializeRequest(request);
            SendMessage(json);
        }

        public Request DeserializeRequest()
        {
            string json = ReceiveMessage();
            return JsonSerializerHelper.DeserializeRequest(json);
        }

        public void SerializeResponse(Response response)
        {
            if (response == null)
                throw new ArgumentNullException(nameof(response));

            string json = JsonSerializerHelper.SerializeResponse(response);
            SendMessage(json);
        }

        public Response DeserializeResponse()
        {
            string json = ReceiveMessage();
            return JsonSerializerHelper.DeserializeResponse(json);
        }

        private void SendMessage(string message)
        {
            try
            {
                byte[] data = encoding.GetBytes(message);
                byte[] lengthPrefix = BitConverter.GetBytes(data.Length);
                
                stream.Write(lengthPrefix, 0, 4);
                stream.Write(data, 0, data.Length);
                stream.Flush();
            }
            catch (Exception ex)
            {
                throw new IOException("Error sending message over network stream", ex);
            }
        }

        private string ReceiveMessage()
        {
            try
            {
                byte[] lengthPrefix = new byte[4];
                int bytesRead = stream.Read(lengthPrefix, 0, 4);

                if (bytesRead == 0)
                    throw new IOException("Connection closed by remote host");

                if (bytesRead < 4)
                    throw new IOException($"Invalid message length prefix: got {bytesRead} bytes instead of 4");

                int messageLength = BitConverter.ToInt32(lengthPrefix, 0);

                if (messageLength <= 0 || messageLength > 10 * 1024 * 1024) 
                    throw new IOException($"Invalid message length: {messageLength}");

                byte[] data = new byte[messageLength];
                int totalBytesRead = 0;

                while (totalBytesRead < messageLength)
                {
                    bytesRead = stream.Read(data, totalBytesRead, messageLength - totalBytesRead);
                    if (bytesRead == 0)
                        throw new IOException($"Connection closed before message complete. Expected {messageLength} bytes, got {totalBytesRead}");
                    totalBytesRead += bytesRead;
                }

                return encoding.GetString(data, 0, messageLength);
            }
            catch (Exception ex)
            {
                throw new IOException($"Error receiving message from network stream: {ex.Message}", ex);
            }
        }
    }
}

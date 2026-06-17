using System;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using System.Diagnostics;

namespace ProiectMPP.Client.Rest
{
    public class LoggingHandler : DelegatingHandler
    {
        private static TestOutputCapture _outputCapture = new TestOutputCapture();

        public LoggingHandler(HttpMessageHandler innerHandler) : base(innerHandler) { }

        public static TestOutputCapture GetOutputCapture() => _outputCapture;

        protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            string requestLog = $"--> STEP: Sending {request.Method} to {request.RequestUri}";

            LogMessage(requestLog);

            if (request.Content != null)
            {
                var content = await request.Content.ReadAsStringAsync();
                string payloadLog = $"Payload: {content}";
                LogMessage(payloadLog);
            }

            var response = await base.SendAsync(request, cancellationToken);

            string responseLog = $"<-- STEP: Received Response Status: {response.StatusCode}";
            LogMessage(responseLog);

            return response;
        }

        private static void LogMessage(string message)
        {
            Debug.WriteLine(message);
            Trace.WriteLine(message);
            _outputCapture.WriteLine(message);
        }
    }
}
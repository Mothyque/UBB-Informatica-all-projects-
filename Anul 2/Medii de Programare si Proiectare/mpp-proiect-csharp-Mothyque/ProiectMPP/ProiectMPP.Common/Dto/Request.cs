using System;

namespace ProiectMPP.Common.dto
{
    [Serializable]
    public class Request
    {
        private readonly RequestType type;
        private readonly object data;

        public Request(RequestType type, object data)
        {
            this.type = type;
            this.data = data;
        }

        public RequestType Type
        {
            get { return type; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
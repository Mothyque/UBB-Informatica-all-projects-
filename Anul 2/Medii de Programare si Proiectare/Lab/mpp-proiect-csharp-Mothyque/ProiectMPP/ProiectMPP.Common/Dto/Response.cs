using System;

namespace ProiectMPP.Common.dto
{
    [Serializable]
    public class Response
    {
        private readonly ResponseType type;
        private readonly object data;

        public Response(ResponseType type, object data)
        {
            this.type = type;
            this.data = data;
        }

        public ResponseType Type
        {
            get { return type; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
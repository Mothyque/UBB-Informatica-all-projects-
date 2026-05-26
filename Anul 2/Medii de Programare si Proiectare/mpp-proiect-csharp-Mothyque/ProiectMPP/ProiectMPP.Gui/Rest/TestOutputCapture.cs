using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ProiectMPP.Client.Rest
{
    public class TestOutputCapture : TextWriter
    {
        private List<string> _output = new List<string>();

        public override Encoding Encoding => Encoding.UTF8;

        public override void WriteLine(string value)
        {
            if (!string.IsNullOrEmpty(value))
            {
                _output.Add(value);
            }
        }

        public override void Write(string value)
        {
            if (!string.IsNullOrEmpty(value))
            {
                _output.Add(value);
            }
        }

        public override void Write(char value)
        {
            _output.Add(value.ToString());
        }

        public override void WriteLine()
        {
            _output.Add(Environment.NewLine);
        }

        public string GetOutput()
        {
            return string.Join(Environment.NewLine, _output);
        }

        public void Clear()
        {
            _output.Clear();
        }

        public static string GetFormattedOutput(string output)
        {
            if (string.IsNullOrEmpty(output))
                return "No output captured.";

            return output;
        }
    }
}

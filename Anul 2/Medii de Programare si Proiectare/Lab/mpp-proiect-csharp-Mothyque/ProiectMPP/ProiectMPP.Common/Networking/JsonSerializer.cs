using ProiectMPP.Common.Domain;
using ProiectMPP.Common.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace ProiectMPP.Common.Networking
{
    public static class JsonSerializerHelper
    {
        private static readonly JsonSerializerOptions Options = new JsonSerializerOptions
        {
            WriteIndented = false,
            PropertyNameCaseInsensitive = true,
            PropertyNamingPolicy = null,
            Converters =
            {
                new JsonStringEnumConverter(),
                new RequestConverter(),
                new ResponseConverter(),
                new UserConverter(),
                new MatchConverter(),
                new ClientConverter(),
                new TicketConverter(),
                new ClientTicketDTOConverter(),
                new ObjectConverter()
            },
            DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull
        };

        public static string SerializeRequest(Request request)
        {
            return JsonSerializer.Serialize(request, Options);
        }

        public static Request DeserializeRequest(string json)
        {
            return JsonSerializer.Deserialize<Request>(json, Options);
        }

        public static string SerializeResponse(Response response)
        {
            return JsonSerializer.Serialize(response, Options);
        }

        public static Response DeserializeResponse(string json)
        {
            return JsonSerializer.Deserialize<Response>(json, Options);
        }
    }

    public class RequestConverter : JsonConverter<Request>
    {
        public override Request Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            RequestType reqType = RequestType.LOGIN;
            object data = null;

            if (reader.TokenType != JsonTokenType.StartObject)
                throw new JsonException("Expected StartObject token");

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "type":
                            if (Enum.TryParse<RequestType>(reader.GetString(), true, out var type))
                                reqType = type;
                            break;
                        case "data":
                            if (reader.TokenType != JsonTokenType.Null)
                            {
                                data = JsonSerializer.Deserialize<object>(ref reader, options);
                            }
                            break;
                    }
                }
            }
            return new Request(reqType, data);
        }

        public override void Write(Utf8JsonWriter writer, Request value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteString("type", value.Type.ToString());
            writer.WritePropertyName("data");
            if (value.Data == null)
                writer.WriteNullValue();
            else
                JsonSerializer.Serialize(writer, value.Data, value.Data.GetType(), options);
            writer.WriteEndObject();
        }
    }

    public class ResponseConverter : JsonConverter<Response>
    {
        public override Response Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            ResponseType respType = ResponseType.OK;
            object data = null;

            if (reader.TokenType != JsonTokenType.StartObject)
                throw new JsonException("Expected StartObject token");

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "type":
                            if (Enum.TryParse<ResponseType>(reader.GetString(), true, out var type))
                                respType = type;
                            break;
                        case "data":
                            if (reader.TokenType != JsonTokenType.Null)
                            {
                                data = JsonSerializer.Deserialize<object>(ref reader, options);
                            }
                            break;
                    }
                }
            }
            return new Response(respType, data);
        }

        public override void Write(Utf8JsonWriter writer, Response value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteString("type", value.Type.ToString());
            writer.WritePropertyName("data");
            if (value.Data == null)
                writer.WriteNullValue();
            else
                JsonSerializer.Serialize(writer, value.Data, value.Data.GetType(), options);
            writer.WriteEndObject();
        }
    }

    public class UserConverter : JsonConverter<User>
    {
        public override User Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var user = new User();
            if (reader.TokenType != JsonTokenType.StartObject)
                return user;

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "id":
                            if (reader.TryGetInt32(out int id))
                                user.Id = id;
                            break;
                        case "username":
                            user.Username = reader.GetString();
                            break;
                        case "password":
                            user.Password = reader.GetString();
                            break;
                    }
                }
            }
            return user;
        }

        public override void Write(Utf8JsonWriter writer, User value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteNumber("id", value.Id);
            writer.WriteString("username", value.Username);
            writer.WriteString("password", value.Password);
            writer.WriteEndObject();
        }
    }

    public class MatchConverter : JsonConverter<Match>
    {
        public override Match Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var match = new Match();
            if (reader.TokenType != JsonTokenType.StartObject)
                return match;

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "id":
                            if (reader.TryGetInt32(out int id))
                                match.Id = id;
                            break;
                        case "teama":
                            match.TeamA = reader.GetString();
                            break;
                        case "teamb":
                            match.TeamB = reader.GetString();
                            break;
                        case "matchtype":
                            match.MatchType = reader.GetString();
                            break;
                        case "ticketprice":
                            if (reader.TryGetDouble(out double price))
                                match.TicketPrice = price;
                            break;
                        case "totalseats":
                            if (reader.TryGetInt32(out int total))
                                match.TotalSeats = total;
                            break;
                        case "availableseats":
                            if (reader.TryGetInt32(out int available))
                                match.AvailableSeats = available;
                            break;
                    }
                }
            }
            return match;
        }

        public override void Write(Utf8JsonWriter writer, Match value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteNumber("id", value.Id);
            writer.WriteString("teamA", value.TeamA);
            writer.WriteString("teamB", value.TeamB);
            writer.WriteString("matchType", value.MatchType);
            writer.WriteNumber("ticketPrice", value.TicketPrice);
            writer.WriteNumber("totalSeats", value.TotalSeats);
            writer.WriteNumber("availableSeats", value.AvailableSeats);
            writer.WriteEndObject();
        }
    }

    public class ClientConverter : JsonConverter<Client>
    {
        public override Client Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var client = new Client();
            if (reader.TokenType != JsonTokenType.StartObject)
                return client;

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "id":
                            if (reader.TryGetInt32(out int id))
                                client.Id = id;
                            break;
                        case "name":
                            client.Name = reader.GetString();
                            break;
                        case "address":
                            client.Address = reader.GetString();
                            break;
                    }
                }
            }
            return client;
        }

        public override void Write(Utf8JsonWriter writer, Client value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteNumber("id", value.Id);
            writer.WriteString("name", value.Name);
            writer.WriteString("address", value.Address);
            writer.WriteEndObject();
        }
    }
    public class TicketConverter : JsonConverter<Ticket>
    {
        public override Ticket Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var ticket = new Ticket();
            if (reader.TokenType != JsonTokenType.StartObject)
                return ticket;

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "id":
                            if (reader.TryGetInt32(out int id))
                                ticket.Id = id;
                            break;
                        case "clientid":
                            if (reader.TryGetInt32(out int clientId))
                                ticket.ClientId = clientId;
                            break;
                        case "matchid":
                            if (reader.TryGetInt32(out int matchId))
                                ticket.MatchId = matchId;
                            break;
                        case "seatlocation":
                            ticket.SeatLocation = reader.GetString();
                            break;
                    }
                }
            }
            return ticket;
        }

        public override void Write(Utf8JsonWriter writer, Ticket value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteNumber("id", value.Id);
            writer.WriteNumber("clientId", value.ClientId);
            writer.WriteNumber("matchId", value.MatchId);
            writer.WriteString("seatLocation", value.SeatLocation);
            writer.WriteEndObject();
        }
    }
    public class ClientTicketDTOConverter : JsonConverter<ClientTicketDTO>
    {
        public override ClientTicketDTO Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var dto = new ClientTicketDTO();
            if (reader.TokenType != JsonTokenType.StartObject)
                return dto;

            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    string propertyName = reader.GetString();
                    reader.Read();

                    switch (propertyName.ToLower())
                    {
                        case "id":
                            if (reader.TryGetInt32(out int id))
                                dto.Id = id;
                            break;
                        case "client":
                            dto.Client = JsonSerializer.Deserialize<Client>(ref reader, options);
                            break;
                        case "match":
                            dto.Match = JsonSerializer.Deserialize<Match>(ref reader, options);
                            break;
                        case "numberoftickets":
                            if (reader.TryGetInt32(out int num))
                                dto.NumberOfTickets = num;
                            break;
                    }
                }
            }
            return dto;
        }

        public override void Write(Utf8JsonWriter writer, ClientTicketDTO value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();
            writer.WriteNumber("id", value.Id);
            writer.WritePropertyName("client");
            JsonSerializer.Serialize(writer, value.Client, typeof(Client), options);
            writer.WritePropertyName("match");
            JsonSerializer.Serialize(writer, value.Match, typeof(Match), options);
            writer.WriteNumber("numberOfTickets", value.NumberOfTickets);
            writer.WriteEndObject();
        }
    }
    public class ObjectConverter : JsonConverter<object>
    {
        public override object Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            switch (reader.TokenType)
            {
                case JsonTokenType.True:
                    return true;
                case JsonTokenType.False:
                    return false;
                case JsonTokenType.Number when reader.TryGetInt32(out var intValue):
                    return intValue;
                case JsonTokenType.Number when reader.TryGetInt64(out var longValue):
                    return longValue;
                case JsonTokenType.Number:
                    reader.TryGetDouble(out var doubleValue);
                    return doubleValue;
                case JsonTokenType.String when reader.TryGetDateTime(out var dateTime):
                    return dateTime;
                case JsonTokenType.String:
                    return reader.GetString();
                case JsonTokenType.StartArray:
                    return ReadArray(ref reader, options);
                case JsonTokenType.StartObject:
                    return ReadObject(ref reader, options);
                case JsonTokenType.Null:
                    return null;
                default:
                    throw new JsonException($"Unexpected token: {reader.TokenType}");
            }
        }

        public override void Write(Utf8JsonWriter writer, object value, JsonSerializerOptions options)
        {
            if (value == null)
            {
                writer.WriteNullValue();
            }
            else
            {
                Type valueType = value.GetType();
                JsonSerializer.Serialize(writer, value, valueType, options);
            }
        }

        private static object ReadArray(ref Utf8JsonReader reader, JsonSerializerOptions options)
        {
            var list = new List<object>();
            while (reader.Read() && reader.TokenType != JsonTokenType.EndArray)
            {
                if (reader.TokenType != JsonTokenType.Null)
                {
                    var item = JsonSerializer.Deserialize<object>(ref reader, options);
                    if (item is Dictionary<string, object> dict)
                    {
                        item = ConvertToDomainType(dict, options);
                    }
                    list.Add(item);
                }
                else
                {
                    list.Add(null);
                }
            }

            if (list.Count > 0)
            {
                var firstNonNull = list.FirstOrDefault(x => x != null);
                if (firstNonNull != null)
                {
                    Type elementType = firstNonNull.GetType();
                    if (list.All(x => x == null || x.GetType() == elementType))
                    {
                        Array typedArray = Array.CreateInstance(elementType, list.Count);
                        for (int i = 0; i < list.Count; i++)
                        {
                            typedArray.SetValue(list[i], i);
                        }
                        return typedArray;
                    }
                }
            }

            return list.ToArray();
        }

        private static object ReadObject(ref Utf8JsonReader reader, JsonSerializerOptions options)
        {
            var dictionary = new Dictionary<string, object>(StringComparer.OrdinalIgnoreCase);
            while (reader.Read() && reader.TokenType != JsonTokenType.EndObject)
            {
                if (reader.TokenType == JsonTokenType.PropertyName)
                {
                    var propertyName = reader.GetString();
                    reader.Read();

                    if (reader.TokenType == JsonTokenType.Null)
                    {
                        dictionary[propertyName] = null;
                    }
                    else
                    {
                        var converter = new ObjectConverter();
                        dictionary[propertyName] = converter.Read(ref reader, typeof(object), options);
                    }
                }
            }

            return ConvertToDomainType(dictionary, options);
        }

        private static object ConvertToDomainType(Dictionary<string, object> dictionary, JsonSerializerOptions options)
        {
            if (dictionary == null || dictionary.Count == 0)
                return dictionary;

            var keys = new HashSet<string>(dictionary.Keys, StringComparer.OrdinalIgnoreCase);

            foreach (var key in dictionary.Keys.ToList())
            {
                if (dictionary[key] is Dictionary<string, object> nestedDict)
                {
                    dictionary[key] = ConvertToDomainType(nestedDict, options);
                }
                else if (dictionary[key] is object[] array)
                {
                    for (int i = 0; i < array.Length; i++)
                    {
                        if (array[i] is Dictionary<string, object> dictInArray)
                        {
                            array[i] = ConvertToDomainType(dictInArray, options);
                        }
                    }
                }
            }

            if (keys.Contains("username") && keys.Contains("password"))
            {
                var user = new User();
                if (dictionary.TryGetValue("id", out var id) && id is int idInt)
                    user.Id = idInt;
                if (dictionary.TryGetValue("username", out var username))
                    user.Username = username?.ToString();
                if (dictionary.TryGetValue("password", out var password))
                    user.Password = password?.ToString();
                return user;
            }

            if (keys.Contains("teamA") && keys.Contains("teamB") && keys.Contains("matchType"))
            {
                var match = new Match();
                if (dictionary.TryGetValue("id", out var id) && id is int idInt)
                    match.Id = idInt;
                if (dictionary.TryGetValue("teamA", out var teamA))
                    match.TeamA = teamA?.ToString();
                if (dictionary.TryGetValue("teamB", out var teamB))
                    match.TeamB = teamB?.ToString();
                if (dictionary.TryGetValue("matchType", out var matchType))
                    match.MatchType = matchType?.ToString();
                if (dictionary.TryGetValue("ticketPrice", out var ticketPrice))
                {
                    if (ticketPrice is double d)
                        match.TicketPrice = d;
                    else if (double.TryParse(ticketPrice?.ToString(), out var d2))
                        match.TicketPrice = d2;
                }
                if (dictionary.TryGetValue("totalSeats", out var totalSeats) && int.TryParse(totalSeats?.ToString(), out var totalInt))
                    match.TotalSeats = totalInt;
                if (dictionary.TryGetValue("availableSeats", out var availableSeats) && int.TryParse(availableSeats?.ToString(), out var availInt))
                    match.AvailableSeats = availInt;
                return match;
            }

            if (keys.Contains("name") && keys.Contains("address") && !keys.Contains("username"))
            {
                var client = new Client();
                if (dictionary.TryGetValue("id", out var id) && id is int idInt)
                    client.Id = idInt;
                if (dictionary.TryGetValue("name", out var name))
                    client.Name = name?.ToString();
                if (dictionary.TryGetValue("address", out var address))
                    client.Address = address?.ToString();
                return client;
            }

            if (keys.Contains("clientId") && keys.Contains("matchId"))
            {
                var ticket = new Ticket();
                if (dictionary.TryGetValue("id", out var id) && id is int idInt)
                    ticket.Id = idInt;
                if (dictionary.TryGetValue("clientId", out var clientId) && int.TryParse(clientId?.ToString(), out var clientIdInt))
                    ticket.ClientId = clientIdInt;
                if (dictionary.TryGetValue("matchId", out var matchId) && int.TryParse(matchId?.ToString(), out var matchIdInt))
                    ticket.MatchId = matchIdInt;
                if (dictionary.TryGetValue("seatLocation", out var seatLocation))
                    ticket.SeatLocation = seatLocation?.ToString();
                return ticket;
            }

            if (keys.Contains("client") && keys.Contains("match") && keys.Contains("numberOfTickets"))
            {
                var dto = new ClientTicketDTO();
                if (dictionary.TryGetValue("id", out var id) && id is int idInt)
                    dto.Id = idInt;
                if (dictionary.TryGetValue("client", out var client))
                    dto.Client = client as Client;
                if (dictionary.TryGetValue("match", out var match))
                    dto.Match = match as Match;
                if (dictionary.TryGetValue("numberOfTickets", out var numTickets) && int.TryParse(numTickets?.ToString(), out var numInt))
                    dto.NumberOfTickets = numInt;
                return dto;
            }

            return dictionary;
        }
    }
}

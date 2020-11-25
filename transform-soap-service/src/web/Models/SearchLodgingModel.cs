using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.Models
{
    public class SearchLodgingModel
    {

        [JsonProperty("params")]
        public Params2 Params { get; set; }

    }

    public class Params2
    {
        public string Operation { get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public DateTime CheckIn { get; set; }
        public DateTime Checkout { get; set; }
        public int Rooms { get; set; }
        public string Type { get; set; }


    }

}

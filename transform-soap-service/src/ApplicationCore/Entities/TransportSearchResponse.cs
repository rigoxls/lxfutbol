using System;
using System.Collections.Generic;
using System.Text;

namespace ApplicationCore.Entities
{
    public class TransportSearchResponse
    {
        public int Flight { get; set; }
        public string Class { get; set; }
        public string DepartureCity { get; set; }
        public string ArrivalCity { get; set; }
        public DateTime? DepartureDate { get; set; }
        public DateTime? ArrivalDate { get; set; }
        public int Price { get; set; }
        public string Meals { get; set; }
        public string Cabin { get; set; }
        public int Type { get; set; }
    }
}

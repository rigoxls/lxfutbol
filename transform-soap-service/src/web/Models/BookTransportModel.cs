using System;
using System.ComponentModel.DataAnnotations;

namespace web.Models
{
    public class BookTransportModel
    {
        [Required]
        public string Operation { get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public DateTime CheckIn { get; set; }
        public DateTime Checkout { get; set; }
        public int Rooms { get; set; }
        public string Type { get; set; }
        public BookTransportModel() {}

    }
}

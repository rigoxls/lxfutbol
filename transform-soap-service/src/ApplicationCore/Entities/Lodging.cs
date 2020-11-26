using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace ApplicationCore.Entities
{
    public class Lodging
    {
        [Required]
        public string Operation { get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public DateTime CheckIn { get; set; }
        public DateTime Checkout { get; set; }
        public int Rooms { get; set; }
        public string Type { get; set; }

    }
}

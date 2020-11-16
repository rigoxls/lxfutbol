using System;
using System.ComponentModel.DataAnnotations;

namespace web.Models
{
    public class BookTransportModel
    {
        [Required]
        public string Operation { get; set; }
        [Required]
        public string DepartinCity { get; set; }
        [Required]
        public string ArrivingCity { get; set; }
        [Required]
        public DateTime DepartinDate { get; set; }
        public string Cabin { get; set; }
        public string PromotionCode { get; set; }
        public string Class { get; set; }

        public BookTransportModel() {}

    }
}

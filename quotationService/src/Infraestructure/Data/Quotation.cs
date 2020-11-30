using System;
using System.Collections.Generic;

namespace Infraestructure.sakila
{
    public partial class Quotation
    {
        public int Id { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public decimal IdTransport { get; set; }
        public decimal IdSpectacle { get; set; }
        public decimal IdLodging { get; set; }
        public decimal NumPeople { get; set; }
        public decimal QuotationStatus { get; set; }
    }
}

using System.ComponentModel.DataAnnotations;

namespace quotationService.Controllers.Api
{
    public class QuotationDto
    {
        [Required]
        public long FechaInicio { get; set; }
        [Required]
        public long FechaFin { get; set; }
        [Required]
        public int IdActividad { get; set; }
        [Required]
        public int IdTransporte { get; set; }
        [Required]
        public int IdHospedaje { get; set; }
        [Required]
        public int NumPersonas { get; set; }
        [Required]
        public int EstadoCotizacion { get; set; }
    }
}
namespace PaymentService.Controllers
{
    public class PaymentDTO
    {
        public int Total { get; set; }
        public string Items { get; set; }
        public int Quantity { get; set; }
    }
}
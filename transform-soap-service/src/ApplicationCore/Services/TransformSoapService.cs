using ApplicationCore.Entities;
using ApplicationCore.Interfaces;
using AAFlightService;
using AviancaServices;
using System.Threading.Tasks;


namespace ApplicationCore.Services
{
    public class TransformSoapService : ITransformSoapService
    {
        private readonly AAFlightsServiceClient _aFlightsServiceClient= new AAFlightsServiceClient();

        private readonly ServicioAviancaVuelosClient _aviancaServices  = new ServicioAviancaVuelosClient();

        public TransformSoapService()
        {
          
        }

        public string Listener(int idProvider, Transport Message, string Type)
        {
            string jsonResult = null;

            return jsonResult;
        }


        public async Task<searchFlightResponse> AASearhFlight(searchFlightRequest Message)
        {
            return await _aFlightsServiceClient.searchFlightAsync(Message.departinCity, Message.arrivingCity, Message.departinDate, Message.cabin, Message.PromotionCode);
        }


        public async Task<consultarVueloResponse> Avia(consultarVueloRequest Message)
        {
            return await _aviancaServices.consultarVueloAsync(Message.ciudadOrigen, Message.ciudadDestino, Message.fechaSalida, Message.clase);
        }
    }
}

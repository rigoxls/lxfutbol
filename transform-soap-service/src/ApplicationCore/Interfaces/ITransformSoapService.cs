using ApplicationCore.Entities;
using AAFlightService;
using System.Threading.Tasks;

namespace ApplicationCore.Interfaces
{
    public interface ITransformSoapService
    {
        public string Listener(int idProvider, Transport Message,string Type);
        
        public Task<TransportSearchResponse> AASearhFlight(Transport Message, int idProvider);


    }
}
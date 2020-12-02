using ApplicationCore.Entities;
using AAFlightService;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace ApplicationCore.Interfaces
{
    public interface ITransformSoapService
    {
        public List<TransportSearchResponse> Listener(int idProvider, Transport Message,string Type);

        public List<LodgingSearchResponse> ListenerLodge(int idProvider, Lodging Message, string Type);

        public Task<TransportSearchResponse> AASearhFlight(Transport Message, int idProvider);
    }
}
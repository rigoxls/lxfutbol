using ApplicationCore.Entities;
using ApplicationCore.Interfaces;
using AAFlightService;
using AviancaServices;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System;
using HiltonRoomServices;
using HiltonBookingServices;

namespace ApplicationCore.Services
{
    public class TransformSoapService : ITransformSoapService
    {

        //provides services
        private readonly AAFlightsServiceClient _aFlightsServiceClient = new AAFlightsServiceClient();

        private readonly ServicioAviancaVuelosClient _aviancaServices  = new ServicioAviancaVuelosClient();

        private readonly HiltonRoomServiceClient _hiltonRoomServiceClient = new HiltonRoomServiceClient();

        private readonly HiltonBookingServiceClient _hiltonBookingServices = new HiltonBookingServiceClient();


        //responses
        private readonly TransportSearchResponse SearchResponse = new TransportSearchResponse();

        private readonly TransportBookResponse BookResponse = new TransportBookResponse();

        private readonly LodgingSearchResponse lodgingSearchResponse = new LodgingSearchResponse();

        private readonly LodgingBookResponse lodgingBookResponse = new LodgingBookResponse();


        //variables
        private Lodging lodging;
      
        private Transport transport;

        public TransformSoapService()
        {
          
        }

        public string Listener(int idProvider, Transport transport, string Type)
        {
            CheckProvider(Type, transport.Operation, transport, lodging,idProvider);
            string jsonResult = transport.Operation == "search" ? JsonConvert.SerializeObject(SearchResponse) : JsonConvert.SerializeObject(BookResponse);
            return jsonResult;
        }

        public string ListenerLodge(int idProvider, Lodging lodging, string Type)
        {
            CheckProvider(Type, lodging.Operation, transport, lodging, idProvider);
            string jsonResult = lodging.Operation == "search" ? JsonConvert.SerializeObject(lodgingSearchResponse) : JsonConvert.SerializeObject(lodgingBookResponse);
            return jsonResult;
        }

        private void CheckProvider(string type, string operation, Transport message, Lodging lodging, int idProvider)
        {
            if (type == "Transport")
            {
                switch (operation)
                {
                    case "search":
                        _ = idProvider != 1 ? AviancaSearch(message, idProvider).Result : AASearhFlight(message, idProvider).Result;
                        break;
                    case "book":
                         _ = idProvider != 1 ? AviancaBook(message).Result : AAFlightBook(message).Result;
                        break;
                }
            }
            else if (type == "Lodging")
            {
                switch (operation)
                {
                    case "search":
                       _ = HiltonSearch(lodging, idProvider).Result;

                        break;
                    case "book":

                        _ = HiltonSearch(lodging, idProvider).Result;

                        break;
                }
            }
        }

        public async Task<TransportSearchResponse> AASearhFlight(Transport Message, int idProvider)
        {
            searchFlightRequest request = new searchFlightRequest(Message.DepartinCity,Message.ArrivingCity,Message.DepartinDate,Message.Cabin, Message.PromotionCode);
            searchFlightResponse response = await _aFlightsServiceClient.searchFlightAsync(request.departinCity, request.arrivingCity, request.departinDate, request.cabin, request.PromotionCode);
            foreach (Trip trip in response.result)
            {
                SearchResponse.IdProvider = idProvider;
                SearchResponse.ArrivalCity = trip.flights[0].arrivingCity;
                SearchResponse.DepartureCity = trip.flights[0].departinCity;
                SearchResponse.ArrivalDate = trip.flights[0].arrivingDate;
                SearchResponse.DepartureDate = trip.flights[0].departinDate;
                SearchResponse.Flight = trip.flights[0].number;
                SearchResponse.Price = (int)trip.flights[0].price;
            }
            return SearchResponse;
        }


        public async Task<TransportBookResponse> AAFlightBook(Transport Message)
        {
            bookFligthRequest fligthRequest = new bookFligthRequest();
            bookFligthResponse bookFligth = await _aFlightsServiceClient.bookFligthAsync(fligthRequest.f, fligthRequest.passengerName);
            BookResponse.Result = bookFligth.result;
            return BookResponse;
        }


        public async Task<TransportSearchResponse> AviancaSearch(Transport Message, int idProvider)
        {
            consultarVueloRequest request = new consultarVueloRequest(Message.DepartinCity, Message.ArrivingCity, Message.DepartinDate, Message.Cabin);
            consultarVueloResponse consultarVuelo = await _aviancaServices.consultarVueloAsync(request.ciudadOrigen, request.ciudadDestino, request.fechaSalida, request.clase);
            foreach (Vuelo vuelo in consultarVuelo.result) 
            {
                SearchResponse.IdProvider = idProvider;
                SearchResponse.ArrivalCity = vuelo.ciudadDestino;
                SearchResponse.DepartureCity = vuelo.ciudadOrigen;
                SearchResponse.ArrivalDate = vuelo.fechaLlegada;
                SearchResponse.DepartureDate = vuelo.fechaSalida;
                SearchResponse.Flight = Int32.Parse(vuelo.vuelo);
                SearchResponse.Price = (int)vuelo.precio;
            }
            return SearchResponse;
        }

        public async Task<TransportBookResponse> AviancaBook(Transport Message)
        {
            reservarVueloRequest reservarVuelo = new reservarVueloRequest();
            reservarVueloResponse vueloResponse = await _aviancaServices.reservarVueloAsync(reservarVuelo.vuelo, reservarVuelo.nombrePasajero, reservarVuelo.numeroIdentidadPasajero);
            BookResponse.Result = vueloResponse.result;
            return BookResponse;
        }


        public async Task<LodgingSearchResponse> HiltonSearch(Lodging lodging, int idProvider)
        {
            HiltonRoomServiceProcessRequest hiltonRoomService = new HiltonRoomServiceProcessRequest();
            HiltonRoomServiceProcessResponse processResponse = new HiltonRoomServiceProcessResponse();
            await _hiltonRoomServiceClient.initiateAsync(hiltonRoomService);
            foreach (Room room in processResponse.result)
            {
                lodgingSearchResponse.IdProvider = idProvider;
                lodgingSearchResponse.NumberRoom = room.Number;
                lodgingSearchResponse.Cabin.Name = room.Hotel.Name;
                lodgingSearchResponse.Cabin.Address = room.Hotel.Address;
                lodgingSearchResponse.Cabin.City = room.Hotel.City;
                lodgingSearchResponse.Cabin.Country = room.Hotel.Country;
                lodgingSearchResponse.PriceRoom = Int32.Parse(room.Price.ToString());
                lodgingSearchResponse.TypeRoom = room.Type;
                lodgingSearchResponse.CheckIn = lodging.CheckIn;
                lodgingSearchResponse.CheckIn = lodging.Checkout;
            }

            return lodgingSearchResponse;

        }

  
    }
}

using ApplicationCore.Entities;
using ApplicationCore.Interfaces;
using AAFlightService;
using AviancaServices;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System;

namespace ApplicationCore.Services
{
    public class TransformSoapService : ITransformSoapService
    {
        private readonly AAFlightsServiceClient _aFlightsServiceClient = new AAFlightsServiceClient();

        private readonly ServicioAviancaVuelosClient _aviancaServices  = new ServicioAviancaVuelosClient();

        private readonly TransportSearchResponse SearchResponse = new TransportSearchResponse();

        private readonly TransportBookResponse BookResponse = new TransportBookResponse();

        public TransformSoapService()
        {
          
        }

        public string Listener(int idProvider, Transport Message, string Type)
        {
            CheckProvider(Type, Message.Operation, Message, idProvider);
            string jsonResult = Message.Operation == "search" ? JsonConvert.SerializeObject(SearchResponse) : JsonConvert.SerializeObject(BookResponse);
            return jsonResult;
        }

        private void CheckProvider(string type, string operation, Transport message, int idProvider)
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
                        break;
                    case "book":
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
            consultarVueloRequest request = new consultarVueloRequest(Message.DepartinCity, Message.ArrivingCity, Message.DepartinDate, Message.Class);
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

    }
}

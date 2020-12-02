using ApplicationCore.Entities;
using ApplicationCore.Interfaces;
using AAFlightService;
using AviancaServices;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System;
using HiltonRoomServices;
using HiltonBookingServices;
using System.Collections.Generic;
using System.Linq;

namespace ApplicationCore.Services
{
    public class TransformSoapService : ITransformSoapService
    {

        //provides services
        private readonly AAFlightsServiceClient _aFlightsServiceClient = new AAFlightsServiceClient();

        private readonly ServicioAviancaVuelosClient _aviancaServices = new ServicioAviancaVuelosClient();

        private readonly HiltonRoomServiceClient _hiltonRoomServiceClient = new HiltonRoomServiceClient();

        private readonly HiltonBookingServiceClient _hiltonBookingServices = new HiltonBookingServiceClient();


        //responses
        private TransportSearchResponse SearchResponse = new TransportSearchResponse();

        private readonly TransportBookResponse BookResponse = new TransportBookResponse();

        private readonly LodgingSearchResponse lodgingSearchResponse = new LodgingSearchResponse();

        private readonly LodgingBookResponse lodgingBookResponse = new LodgingBookResponse();


        //variables
        private readonly Lodging lodging;

        private readonly Transport transport;

        private List<TransportSearchResponse> arrayList;

        private List<TransportBookResponse> result;


        private List<LodgingSearchResponse> arrayHotelList;


        public TransformSoapService()
        {

        }

        public List<TransportSearchResponse> Listener(int idProvider, Transport transport, string Type)
        {
            CheckProvider(Type, transport.Operation, transport, lodging, idProvider);
            return  transport.Operation == "search" ? (arrayList) : (arrayList);
             
        }

        public List<LodgingSearchResponse> ListenerLodge(int idProvider, Lodging lodging, string Type)
        {
            CheckProvider(Type, lodging.Operation, transport, lodging, idProvider);
            return lodging.Operation == "search" ? (arrayHotelList) : (arrayHotelList);
            
        }

        private void CheckProvider(string type, string operation, Transport message, Lodging lodging, int idProvider)
        {
            if (type == "Transport")
            {
                switch (operation)
                {
                    case "search":
                        _ = idProvider != 4 ? AviancaSearch(message, idProvider).Result : AASearhFlight(message, idProvider).Result;
                        break;
                    case "book":
                        _ = idProvider != 4 ? AviancaBook(message).Result : AAFlightBook(message).Result;
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
            searchFlightRequest request = new searchFlightRequest(Message.DepartinCity, Message.ArrivingCity, Message.DepartinDate, Message.Cabin, Message.PromotionCode);
            searchFlightResponse response = await _aFlightsServiceClient.searchFlightAsync(request.departinCity, request.arrivingCity, request.departinDate, request.cabin, request.PromotionCode);
            arrayList = new List<TransportSearchResponse>();
            Trip[] trip = response.result;

            foreach (Trip t in trip)
            {
                foreach (Flight flights in t.flights)
                {
                    SearchResponse.ArrivalCity = flights.arrivingCity;
                    SearchResponse.DepartureCity = flights.departinCity;
                    SearchResponse.ArrivalDate = flights.arrivingDate;
                    SearchResponse.DepartureDate = flights.departinDate;
                    SearchResponse.Flight = flights.number;
                    SearchResponse.Meals = flights.meals;
                    SearchResponse.Cabin = flights.cabin;
                    SearchResponse.Price = (int)flights.price;
                    arrayList.Add(SearchResponse);
                }
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
            arrayList = new List<TransportSearchResponse>();
            foreach (Vuelo vuelo in consultarVuelo.result)
            {
                SearchResponse.ArrivalCity = vuelo.ciudadDestino;
                SearchResponse.DepartureCity = vuelo.ciudadOrigen;
                SearchResponse.ArrivalDate = vuelo.fechaLlegada;
                SearchResponse.DepartureDate = vuelo.fechaSalida;
                SearchResponse.Flight = Int32.Parse(vuelo.vuelo);
                SearchResponse.Price = (int)vuelo.precio;
                SearchResponse.Class = vuelo.clase;
                arrayList.Add(SearchResponse);
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
            arrayHotelList = new List<LodgingSearchResponse>();
            initiateResponse initiate = await _hiltonRoomServiceClient.initiateAsync(hiltonRoomService);
            foreach (Room room in initiate.HiltonRoomServiceProcessResponse.result)
            {
                lodgingSearchResponse.NumberRoom = room.Number;
                lodgingSearchResponse.Hotel = new Cabin();
                lodgingSearchResponse.Hotel.Name = room.Hotel.Name;
                lodgingSearchResponse.Hotel.Address = room.Hotel.Address;
                lodgingSearchResponse.Hotel.City = room.Hotel.City;
                lodgingSearchResponse.Hotel.Country = room.Hotel.Country;
                lodgingSearchResponse.PriceRoom = Int32.Parse(room.Price.ToString());
                lodgingSearchResponse.TypeRoom = room.Type;
                lodgingSearchResponse.CheckIn = lodging.CheckIn;
                lodgingSearchResponse.CheckIn = lodging.CheckOut;
                arrayHotelList.Add(lodgingSearchResponse);

            }

            return lodgingSearchResponse;

        }


    }
}

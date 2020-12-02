using System;
using ApplicationCore.Entities;
using ApplicationCore.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Caching.Distributed;
using web.Models;

namespace web.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TransformSoapController : Controller
    {
        private readonly ITransformSoapService _transformSoapService;

        private readonly IDistributedCache _distributedCache;

        private readonly string PROVIDER_TEMPLATE_CACHE = "PROVIDER_TEMPLATE";


        public TransformSoapController( ITransformSoapService transformSoapService, IDistributedCache distributedCache)
        {
            _distributedCache = distributedCache;
            _transformSoapService = transformSoapService;
        }

        // POST: api/TransformSoap/transform/2
        [HttpPost("transform/{idProvider}")]
        public ActionResult Transform(int idProvider, [FromBody] SearchTransportModel Message)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }
                string template = _distributedCache.GetString(PROVIDER_TEMPLATE_CACHE);
                Params search = Message.Params;
                string Type = "Transport";
                Transport transport = new Transport()
                {
                    Operation = search.Operation,
                    ArrivingCity = search.ArrivingCity,
                    DepartinCity = search.DepartinCity,
                    DepartinDate = search.DepartinDate
                };
                return Ok(_transformSoapService.Listener(idProvider, transport, Type));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                return StatusCode(500);
            }
        }


        //POST: api/TransformSoap/transformLodge/2
        [HttpPost("transformLodge/{idProvider}")]
        public ActionResult TransformLodge(int idProvider, [FromBody] SearchLodgingModel Message)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }
                string template = _distributedCache.GetString(PROVIDER_TEMPLATE_CACHE);
                Lodge search = Message.Params;
                string Type = "Lodging";
                Lodging lodging = new Lodging() {
                    Operation = search.Operation,
                    CheckIn = search.CheckIn,
                    CheckOut = search.CheckOut
                };
                return Ok(_transformSoapService.ListenerLodge(idProvider, lodging, Type));
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }




    }
}

using System;
using System.Text.Json;
using ApplicationCore.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using web.Models;

namespace web.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TransformSoapController : ControllerBase
    {
        private readonly ITransformSoapService _transformSoapService;

        private readonly IDistributedCache _distributedCache;

        private readonly string PROVIDER_TEMPLATE_CACHE = "PROVIDER_TEMPLATE";

        private readonly ILogger<TransformSoapController> _logger;

        public TransformSoapController(ILogger<TransformSoapController> logger, ITransformSoapService transformSoapService, IDistributedCache distributedCache)
        {
            _distributedCache = distributedCache;
            _transformSoapService = transformSoapService;
            _logger = logger;
        }


        // POST: api/TransformSoap/transformSpectacle/2
        [HttpPost("transform/{idProvider}")]
        public ActionResult Transform(int idProvider, [FromBody] dynamic Message)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }
                string template = _distributedCache.GetString(PROVIDER_TEMPLATE_CACHE);
                JsonElement je = Message.GetProperty("params");
                SearchTransportModel search = Message;


                string Type = "Transport";
                return Ok(_transformSoapService.Listener(idProvider, Message.Transport, Type)
);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

    }
}

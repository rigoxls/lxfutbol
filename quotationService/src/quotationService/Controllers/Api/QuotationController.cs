using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Infraestructure;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace quotationService.Controllers.Api
{
    [Route("api/[controller]")]
    [ApiController]
    public class QuotationController : ControllerBase
    {
        private readonly QuotationDbContext _context;

        public QuotationController(QuotationDbContext context)
        {
            _context = context;
        }
        // GET: api/<QuotationController>
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<QuotationController>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<QuotationController>
        [HttpPost]
        public IActionResult CreateQuotation([FromBody] QuotationDto quotation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
           // _context.Quotation.Add(quotation);


            return  ;

        }


        // PUT api/<QuotationController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<QuotationController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}

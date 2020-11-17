using System.Collections.Generic;
using Infraestructure.Data;
using Microsoft.AspNetCore.Mvc;


// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace quotationService.Controllers.Api
{
    [ApiController]
    [Route("api/[controller]")]
    public class QuotationController : ControllerBase
    {
        private readonly QuotationContext _context;

        public QuotationController(QuotationContext context)
        {
            _context = context;
        }


        // GET api/<QuotationController>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        //POST api/<QuotationController>
        [HttpPost]
        public IActionResult Task<CreateQuotation>([FromBody] Quotation quotation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            return (IActionResult)_context.Quotation.Add(quotation);
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

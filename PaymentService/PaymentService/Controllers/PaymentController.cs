using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Stripe;
using Stripe.Checkout;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace PaymentService.Controllers
{
    [ApiController]
    [Route("api/PaymentController")]
    public class PaymentController : Controller
    {
        public PaymentController()
        {
            StripeConfiguration.ApiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

        }
        // Set your secret key. Remember to switch to your live secret key in production!
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        // GET: api/<controller>
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<controller>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        [HttpPost("create-checkout-session")]
        public ActionResult CreateCheckoutSession(PaymentDTO payment)
        {
            var domain = "http://localhost:30012";
            var options = new SessionCreateOptions
            {
                PaymentMethodTypes = new List<string>
        {
          "card",
        },
                LineItems = new List<SessionLineItemOptions>
        {
          new SessionLineItemOptions
          {
            PriceData = new SessionLineItemPriceDataOptions
            {
              UnitAmount = payment.Quantity,
              Currency = "USD",
              ProductData = new SessionLineItemPriceDataProductDataOptions
              {
                Name = payment.Items,
              },
            },
            Quantity = payment.Quantity,
          },
        },
                Mode = "payment",
                SuccessUrl = domain+"/success",
                CancelUrl = domain+"/cancel",
            };

            var service = new SessionService();
            Session session = service.Create(options);

            return Json(new { id = session.Id });
        }

        // PUT api/<controller>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<controller>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}

using System;

namespace ApplicationCore.Entities
{
	public class LodgingSearchResponse
	{
		public string NumberRoom { get; set; }

		public Cabin Hotel { get; set; }
		public DateTime CheckIn { get; set; }
		public DateTime CheckOut { get; set; }

		public int PriceRoom { get; set; }
		public string TypeRoom { get; set; }




	}

	public class Cabin
	{
		public string Name { get; set; }
		public string Address { get; set; }
		public string City { get; set; }
		public string Country { get; set; }

		public Cabin()
		{

		}
	}
}

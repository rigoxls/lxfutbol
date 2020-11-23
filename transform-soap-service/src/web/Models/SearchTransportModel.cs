using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using ApplicationCore.Entities;
using Newtonsoft.Json;

namespace web.Models
{
    public class SearchTransportModel
    {
       [JsonProperty("params")]
        public Params Params { get; set; }
    }
    public class Params
    {
         public string Operation  { get; set; }
         public string DepartinCity { get; set; }
         public string ArrivingCity { get; set; }
         public DateTime DepartinDate { get; set; }
     
    }

}

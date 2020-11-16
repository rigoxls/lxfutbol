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
        public Transport Transport { get; set; }
        public SearchTransportModel() { }

    }

}

using Apache.Ignite.Core.Cache.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteCacheSearch
{
    public class Details
    {
            [QueryTextField]
            public string Name { get; set; }
           
            [QueryTextField]
            public int Age { get; set; }

            [QueryTextField]
            public int Descripition { get; set; }

        public override string ToString()
            {
                return $"[Name={Name}, Age={Age}]";
            }
    }
  }

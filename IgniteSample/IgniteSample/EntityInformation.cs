using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteSample
{
   public class EntityInformation
    {
        public string EntityId { get; set; }
        public string TenantName { get; set; }
        public string TenantId { get; set; }
        public string EntityName { get; set; }
        public string BoundaryId { get; set; }
        public string AreaId { get; set; }
        public string EntityInfoJson { get; set; }
        public string CreatedBy { get; set; }
        public string ModifiedBy { get; set; }
    }
}

using Apache.Ignite.Core.Binary;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteSample
{
    class IgniteSerialization : IBinarySerializer
    {

       
        public void ReadBinary(object obj, IBinaryReader reader)
        {
            throw new NotImplementedException();
        }

        public void WriteBinary(object obj, IBinaryWriter writer)
        {
            throw new NotImplementedException();
        }
    }
}

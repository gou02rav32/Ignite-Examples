using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteCacheSearch
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            IgniteEntitySearch ig = new IgniteEntitySearch();
            String val = IgniteEntitySearch.GetSearch();
            Console.WriteLine(val);
            Console.ReadKey();

        }
    }
}

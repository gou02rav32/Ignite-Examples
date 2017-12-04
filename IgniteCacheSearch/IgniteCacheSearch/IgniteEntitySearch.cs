using Apache.Ignite.Core;
using Apache.Ignite.Core.Binary;
using Apache.Ignite.Core.Cache;
using Apache.Ignite.Core.Cache.Configuration;
using Apache.Ignite.Core.Cache.Query;
using Apache.Ignite.Core.Discovery.Tcp;
using Apache.Ignite.Core.Discovery.Tcp.Static;
using Apache.Ignite.Core.Events;
using Apache.Ignite.Core.PersistentStore;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteCacheSearch
{
    class IgniteEntitySearch
    {
        public static String GetSearch()
        {
            /* var config = new IgniteConfiguration
             {
                 DiscoverySpi = new TcpDiscoverySpi
                 {
                     IpFinder = new TcpDiscoveryStaticIpFinder
                     {
                         Endpoints = new[] { "192.168.1.38:47500..47509" }
                     },
                     SocketTimeout = TimeSpan.FromSeconds(0.3)
                 },
                 IncludedEventTypes = EventType.CacheAll,
                 PersistentStoreConfiguration = new PersistentStoreConfiguration()

             };*/

      


            // var stateListData = "[{ 123 : { name: 'Alabama', abbreviation: 'AL' }}, {124 : { name: 'Alaska', abbreviation: 'AK' }}, {125 : { name: 'AmericanSamoa', abbreviation: 'AS' }}, {126 : { name: 'Arizona', abbreviation: 'AZ' }}, {127 : { name: 'Arkansas', abbreviation: 'AR' }}, { 128 : { name: 'California', abbreviation: 'CA' }}, { 129 : { name: 'Colorado', abbreviation: 'CO' }}]";
            //List<String> records = JsonConvert.DeserializeObject<List<String>>(stateListData);

            Ignition.ClientMode = true;
            /* var cfg = new IgniteConfiguration
             {
                 // Register custom class for Ignite serialization
                 BinaryConfiguration = new BinaryConfiguration(typeof(Details))
             };*/
            IIgnite ignite = Ignition.Start();
            var cache = ignite.GetOrCreateCache<int, Details>(
             new CacheConfiguration("DetailsCache", typeof(Details)));


            String value = null;
           // ICache<int, Details> cache = ignite.GetOrCreateCache<int, Details>("details");
            cache[1] = new Details { Name = "John Doe", Age = 20 };
            cache[2] = new Details { Name = "Fred", Age = 23 };
            cache[3] = new Details { Name = "John moe", Age = 18 };
            cache[4] = new Details { Name = "Anglina Chris", Age = 35 };
            cache[5] = new Details { Name = "Donna Paul Anderson", Age = 45 };
            cache[6] = new Details { Name = "Donna Spectre", Age = 48 };
            cache[7] = new Details { Name = "Mike Ross", Age = 27 };
            cache[8] = new Details { Name = "Louis Litt", Age = 57 };
            cache[9] = new Details { Name = "Scottie", Age = 38 };
            cache[10] = new Details { Name = "Scottie", Age = 28 };

            //cache.Put()

            // Query for all people with "Master Degree" in their resumes.


            //IIgnite ignite = Ignition.Start();
            // ICache<String, String> cache = ignite.GetOrCreateCache<String, String>("SearchListData");
            // for (int i = 0; i < 10; i++)
            //   cache.Put(i, cache[i]);

            /* IQueryCursor<ICacheEntry<int, Details>> cursor = cache.Query(new TextQuery(typeof(Details), "J*n*"));

              foreach (var cacheEntry in cursor)
                  Console.WriteLine(cacheEntry);*/


            ArrayList list = new ArrayList();
            foreach (ICacheEntry<int, Details> cacheEntry in cache)
            {
                 
                 list.Add(cacheEntry.Key);
                value = cacheEntry.ToString();
               Console.WriteLine(value);
            }
           // JObject json = JObject.Parse(value);
            //  ArrayList list1 = JsonConvert.DeserializeObject(value);
          //  List<Details> list = JsonConvert.DeserializeObject<List<Details>>(value);

           // Console.WriteLine(json);


            //var cache = ignite.GetOrCreateCache<int, Person>("myCache");

            // Query for all people with "Master Degree" in their resumes.
            var cursor1 = cache.Query(new TextQuery("Details", "J*n*"));
             // var query = new TextQuery("","");

            // Iterate over results. Using 'foreach' loop will close the cursor automatically.
            foreach (var cacheEntry in cursor1)
            {
                Console.WriteLine(cacheEntry.Value);
            }
            string jsonString = JsonConvert.SerializeObject(value);
            
            Console.WriteLine(jsonString);
           



            /*for (int i = 0; i < 10; i++)
            {
                value = cache.Get(i).ToString();
                Console.WriteLine("Got [key={0}, val={1}]", i, cache.Get(i).ToString());
            }*/
            //cache.Put(key, );
            // String value = cache.Get(key);
            //Console.WriteLine(value);
            return value;

        }
    }
}

using Apache.Ignite.Core;
using Apache.Ignite.Core.Cache;
using Apache.Ignite.Core.Cache.Configuration;
using Apache.Ignite.Core.Discovery.Tcp;
using Apache.Ignite.Core.Discovery.Tcp.Static;
using Apache.Ignite.Core.Events;
using Apache.Ignite.Core.PersistentStore;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IgniteSample
{
    class Program
    {
        static void Main(string[] args)
        {

            //var cfg = new IgniteConfiguration
            //{
            //    MemoryConfiguration = new MemoryConfiguration
            //    {
            //        DefaultMemoryPolicyName = "defaultPolicy",
            //        MemoryPolicies = new[]
            //            {
            //              new MemoryPolicyConfiguration
            //              {
            //                Name = "defaultPolicy",
            //                InitialSize = 128 * 1024 * 1024,  // 128 MB
            //                MaxSize = 1L * 1024 * 1024 * 1025  // 4 GB
            //              }
            //            }
            //    }
            //};
            //var ignite = Ignition.Start(cfg);

            // new IgniteBasicExample().compute();
            //BasicOperations();
           //PutData();
           CacheInvoke();
        }

        public static void CacheInvoke() {

            try
            {
                Ignition.ClientMode = true;
                var ignite = Ignition.Start(GetIgniteConfiguration());
                //var ignite = Ignition.Start();
                ignite.SetActive(true);
                var cache = ignite.GetOrCreateCache<string, string>("c091e548-b45a-49b4-b8ec-2cb5e27c7af6");

              //  var json = "{\r\n  \"name\": \"pumpJack\",\r\n   \"icon\": \"images/download.png\",\r\n  \"parent\": \"null\",\r\n  \"children\": [\r\n    {\r\n      \"name\": \"Main Shaft\",\r\n      \"icon\": \"images/download (1).png\",\r\n      \"parent\": \"pumpJack\",\r\n      \"children\": [\r\n        {\r\n          \"name\": \"Dril Head\",\r\n             \"icon\": \"images/download (1).png\",\r\n          \"parent\": \"Main Shaft\"\r\n        },\r\n        {\r\n          \"name\": \"Water Pressure Gauge\",\r\n          \"icon\": \"images/download.png\",\r\n          \"parent\": \"Main Shaft\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"name\": \"Safty Fuse System\",\r\n        \"icon\": \"images/download (1).png\",\r\n      \"parent\": \"pumpJack\"\r\n    },\r\n     {\r\n      \"name\": \"Water Pressure Gauge\",\r\n        \"icon\": \"images/download (1).png\",\r\n      \"parent\": \"pumpJack\"\r\n    },  \r\n      {\r\n          \"name\": \"Dril Head\",\r\n             \"icon\": \"images/download (1).png\",\r\n          \"parent\": \"Main Shaft\"\r\n        },\r\n\r\n     {\r\n      \"name\": \"Water Pressure Gauge\",\r\n        \"icon\": \"images/download (1).png\",\r\n      \"parent\": \"pumpJack\"\r\n    },\r\n     {\r\n      \"name\": \"Main Shaft\",\r\n        \"icon\": \"images/download (1).png\",\r\n      \"parent\": \"pumpJack\"\r\n      \r\n    }\r\n  ]\r\n}";

                var json= "{\"EntityId\":\"ideas\",\"TenantName\":\"solar\",\"TenantId\":\"85d8ad2b-a880-43c4-ac8d-331432c078fe\",\"EntityName\":\"Areas\",\"EntityInfoJson\":{\"name\":\"pumpJack\",\"icon\":\"images/download.png\",\"parent\":null,\"children\":[{\"name\":\"Main Shaft\",\"icon\":\"images/download (1).png\",\"parent\":\"pumpJack\",\"id\":\"2d459dc6-f0a8-4d61-b655-4a6a6f9f9467\",\"children\":[{\"name\":\"Dril Head\",\"icon\":\"images/download (1).png\",\"id\":\"c97e0ec3-c7a2-499f-a06d-dfcd0112a599\",\"parent\":\"Main Shaft\"},{\"name\":\"Water Pressure Gauge\",\"icon\":\"images/download.png\",\"id\":\"bf928e7d-5ab8-424d-8398-425e38252e54\",\"parent\":\"Main Shaft\"}]},{\"name\":\"Safty Fuse System\",\"icon\":\"images/download (1).png\",\"id\":\"5f108ce0-70e3-4835-870c-cff790d083df\",\"parent\":\"pumpJack\"},{\"name\":\"Water Pressure Gauge\",\"icon\":\"images/download (1).png\",\"id\":\"1b5e85d9-3870-4894-9305-f03f8174c580\",\"parent\":\"pumpJack\"},{\"name\":\"Dril Head\",\"icon\":\"images/download (1).png\",\"id\":\"611c9131-c0b5-4532-a4b5-4ef5b2649f1b\",\"parent\":\"Main Shaft\"},{\"name\":\"Water Pressure Gauge\",\"icon\":\"images/download (1).png\",\"id\":\"ee4f5bfc-6155-492b-8803-8db95fda1e43\",\"parent\":\"pumpJack\"},{\"name\":\"Main Shaft\",\"icon\":\"images/download (1).png\",\"id\":\"5536affe-b3d1-4393-89a6-0f56e9ad0b55\",\"parent\":\"pumpJack\"}]},\"CreatedBy\":\"chinna\",\"ModifiedBy\":\"chinna\"}";
                var json2 = "{\"name\": \"suppliers\",\"icon\": \"images/download.png\",\"parent\": \"null\",\"children\": [{\"name\": \"ishaBurger\",\"icon\": \"images/People2.png\",\"parent\": \"suppliers\",\"children\": [{\"name\": \"Entrepreneurs\",\"icon\": \"images/People.png\",\"parent\": \"ishaBurger\"},{\"name\": \"chinaExport\",\"icon\": \"images/building.png\",\"parent\": \"ishaBurger\"}]},{\"name\": \"Fedex\",\"icon\": \"images/download.png\",\"parent\": \"suppliers\"},{\"name\": \"canpack\",\"icon\": \"images/download.png\",\"parent\": \"suppliers\"},{\"name\": \"DGlobal\",\"icon\": \"images/download.png\",\"parent\": \"suppliers\"},{\"name\": \"logistics\",\"icon\": \"images/download.png\",\"parent\": \"suppliers\"},{\"name\": \"NinaSamon\",\"icon\": \"images/download.png\",\"parent\": \"suppliers\"}]}";

                var mxdata =@"< root >< mxCell id = '0' />< mxCell id = '1' parent = '0' />< mxCell id = '2' parent = '1' />< mxCell id = '3' parent = '1' />< mxCell id = '4' value = 'Hello,' parent = '1' vertex = '1' >< mxGeometry x = '670' y = '300' width = '80' height = '30' as= 'geometry' /></ mxCell >< mxCell id = '5' value = 'World!' parent = 1' vertex = '1' >< mxGeometry x = '650' y = '140' width = '80' height = '30' as= 'geometry' /></ mxCell >< mxCell id = '6' value = '' parent = '1' source = '4' target = '5' edge = '1' >< mxGeometry relative = '1' as= 'geometry' /></ mxCell ></ root >";
                var ddd = "<root> <mxCell id='0'/> <mxCell id='1' parent='0'/><mxCell id='2' value='&lt;h1 style=&quot;margin:0px;&quot;&gt;Website&lt;/h1&gt;&lt;br&gt;&lt;br&gt;' vertex='1' parent='1'><mxGeometry x='130' y='150' width='120' height='120' as='geometry'><mxRectangle width='120' height='40' as='alternateBounds'/></mxGeometry></mxCell>   <mxCell id='3' value='&lt;h1 style=&quot;margin:0px;&quot;&gt;Website&lt;/h1&gt;&lt;br&gt;&lt;br&gt;' vertex='1' parent='1'><mxGeometry x='590' y='60' width='120' height='120' as='geometry'><mxRectangle width='120' height='40' as='alternateBounds'/></mxGeometry></mxCell><mxCell id='4' value='&lt;h1 style=&quot;margin:0px;&quot;&gt;Website&lt;/h1&gt;&lt;br&gt;&lt;br&gt;' vertex='1' parent='1'><mxGeometry x='400' y='200' width='120' height='120' as='geometry'><mxRectangle width='120' height='40' as='alternateBounds'/></mxGeometry></mxCell><mxCell id='5' value='&lt;h1 style=&quot;margin:0px;&quot;&gt;Website&lt;/h1&gt;&lt;br&gt;&lt;br&gt;' vertex='1' parent='1'><mxGeometry x='280' y='430' width='120' height='120' as='geometry'><mxRectangle width='120' height='40' as='alternateBounds'/></mxGeometry></mxCell><mxCell id='6' value='&lt;h1 style=&quot;margin:0px;&quot;&gt;Website&lt;/h1&gt;&lt;br&gt;&lt;br&gt;' vertex='1' parent='1'><mxGeometry x='740' y='270' width='120' height='120' as='geometry'><mxRectangle width='120' height='40' as='alternateBounds'/></mxGeometry></mxCell><mxCell id='7' value='' edge='1' parent='1' source='6' target='4'><mxGeometry relative='1' as='geometry'/></mxCell><mxCell id='8' value='' edge='1' parent='1' source='6' target='5'><mxGeometry relative='1' as='geometry'/></mxCell><mxCell id='9' value='' edge='1' parent='1' source='2' target='4'><mxGeometry relative='1' as='geometry'/></mxCell><mxCell id='10' value='' edge='1' parent='1' source='2' target='3'><mxGeometry relative='1' as='geometry'/></mxCell></root>";
                EntityInformation inf = new EntityInformation();
                inf.TenantName = "solar";
                inf.TenantId = "85d8ad2b-a880-43c4-ac8d-331432c078fe";
                inf.EntityInfoJson = json;
                EntityInformation inf1 = new EntityInformation();
                inf1.TenantName = "solar";
                inf1.TenantId = "85d8ad2b-a880-43c4-ac8d-331432c078fe";
                inf1.EntityInfoJson = json2;
                var jsonData=  JsonConvert.SerializeObject(inf);
                var jsonData1 = JsonConvert.SerializeObject(inf1);

                cache.Put("ideas", jsonData);

                cache.Put("people", jsonData1);
                cache.Put("5f108ce0-70e3-4835-870c-cff790d083df", ddd);

                var data  =cache.Get("ideas");
                var data1 = cache.Get("people");
                //var data2 = cache.Get("5f108ce0-70e3-4835-870c-cff790d083df");
                //var proc = new Processor();

                //// Increment cache value 10 times
                //for (int i = 0; i < 10; i++)
                //    cache.Invoke(1, proc, 5);

                
            }
            catch (Exception ex) {
                Console.WriteLine(ex.Message);
            }
        }


        public static string PutData() {
            try
            {
                Ignition.ClientMode = true;
                var ignite = Ignition.Start();
               // ignite.SetActive(true);

                var cache = ignite.GetOrCreateCache<string, string>("TenantDetails");
               // cache.Put("demo13", "65b7b6f5-c7a8-4fb2-9d72-0dcc53a96e89");
                cache.Put("solar", "85d8ad2b-a880-43c4-ac8d-331432c078fe");
               var sss = cache.TryGetAsync("solar").GetAwaiter().GetResult();

            }
            catch (Exception ex) {

            }
            return "";
        }
        public static IgniteConfiguration GetIgniteConfiguration()
        {
            var config = new IgniteConfiguration
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

            };
            return config;
        }


        public static void BasicOperations()
        {
            try
            {

                using (var ignite = Ignition.Start())
                {

                    var cache = ignite.GetOrCreateCache<int, string>("TestCache");
                    for (int i = 0; i < 10; i++)
                        cache.Put(i, i.ToString());
                    for (int i = 0; i < 10; i++)
                    {
                        var value = cache.Get(i);

                        Console.WriteLine("Key : " + i + "  Value: " + value);
                    }

                    CacheResult<string> oldVal = cache.GetAndPutIfAbsent(11, "Hello");
                    Console.WriteLine("Value: " + oldVal.Value);

                    bool success = cache.PutIfAbsent(22, "World");
                    Console.WriteLine("Value: " + success);
                    oldVal = cache.GetAndReplace(11, "Hello");
                    Console.WriteLine("Value: " + oldVal.Value);
                    success = cache.Replace(22, "World", "World!");
                    oldVal = cache.GetAndReplace(11, "Hello");
                    Console.WriteLine("Value: " + oldVal.Value);
                    success = cache.Remove(1, "Hello");
                    var value1 = cache.Get(22);
                    Console.WriteLine("Value: " + value1);
                    Console.ReadKey();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public class Processor : ICacheEntryProcessor<int, int, int, int>
        {

            public int Process(IMutableCacheEntry<int, int> entry, int arg)
            {
                var value = entry.Exists ? arg : entry.Value + arg;

                return entry.Value;
            }
        }


        
    }
}

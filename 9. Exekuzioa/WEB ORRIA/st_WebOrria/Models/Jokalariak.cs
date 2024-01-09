using Rentacar.Data;

namespace st_WebOrria.Models
{
    public class Jokalariak
    {

        // Jokalarien parametroak
        public string NAN { get; set; }
        public string izena { get; set; }
        public string abizena { get; set; }
        public int denbora { get; set; }
        public int puntuak { get; set; }


        public class JokalariakZerrenda
        {
            private readonly JokalariakContext _demoDbContext;

            public JokalariakZerrenda(JokalariakContext demoDbContext)
            {
                _demoDbContext = demoDbContext;
            }

            public List<Jokalariak> JokalariakIrakurri()
            {

                List<Jokalariak> jokalariak = _demoDbContext.jokalariak
                    .Select(a => new Jokalariak
                    {
                        NAN = a.NAN,
                        izena = a.izena,
                        abizena = a.abizena,
                        denbora = a.denbora,
                        puntuak = a.puntuak
                    })
                    .ToList();


                Console.WriteLine($"Número de elementos en la lista: {jokalariak.Count}");
                foreach (var item in jokalariak)
                {
                    Console.WriteLine($"Jokalariak: NAN={item.NAN}, Izena={item.izena}, Abizena={item.abizena}, Denbora={item.denbora}, Puntuak={item.puntuak}");
                }

                return jokalariak;
            }

        }
    }
}

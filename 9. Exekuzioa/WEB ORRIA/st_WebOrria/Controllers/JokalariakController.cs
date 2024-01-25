using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using st_WebOrria.Models;
using System.Diagnostics;
using static st_WebOrria.Models.Jokalariak;

namespace st_WebOrria.Controllers
{
    public class JokalariakController : Controller
    {
        /*private readonly JokalariakZerrenda _jokalariakZerrenda;

        public JokalariakController(JokalariakZerrenda jokalariakZerrenda)
        {
            _jokalariakZerrenda = jokalariakZerrenda;
        }*/

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        public IActionResult Ranking()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

    }
}

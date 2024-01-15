using Microsoft.AspNetCore.Mvc;
using st_WebOrria.Models;
using System.Diagnostics;

namespace st_WebOrria.Controllers
{
    public class JokalariakController : Controller
    {
        private readonly ILogger<JokalariakController> _logger;
        private readonly Jokalariak.JokalariakZerrenda _jokalariakZerrenda;

        public JokalariakController(ILogger<JokalariakController> logger, Jokalariak.JokalariakZerrenda jokalariakZerrenda)
        {
            _logger = logger;
            _jokalariakZerrenda = jokalariakZerrenda;
        }

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
            var jokalariak = _jokalariakZerrenda.JokalariakIrakurri();
            return View(jokalariak);
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}

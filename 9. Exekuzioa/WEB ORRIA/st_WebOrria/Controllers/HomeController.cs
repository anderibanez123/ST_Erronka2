using Microsoft.AspNetCore.Mvc;
using st_WebOrria.Models;
using System.Diagnostics;

namespace st_WebOrria.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Hasiera()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }
        public IActionResult Contact()
        {
            return View("Contact");
        }
        public IActionResult Ranking()
        {
            return View("Ranking");
        }


        public IActionResult Proiektuak()
        {
            return View("Proiektuak");
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}

using Microsoft.AspNetCore.Mvc;
using st_WebOrria.Models;
using System.Diagnostics;

namespace st_WebOrria.Controllers
{
    public class JokalariakController : Controller
    {
        private readonly ILogger<JokalariakController> _logger;

        public JokalariakController(ILogger<JokalariakController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
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

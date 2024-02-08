//
//  PlayView.swift
//  St_Galdetegia
//
//  Created by IzarraitzMBP2 on 26/1/24.
//

import SwiftUI

struct PlayView: View {
    @State private var questionNumber = 1
    @State private var timerValue = 30
    @State private var isNavigateToMenuView = false
    @State private var isNavigateToScoreView = false
    @State private var timer: Timer?
    @State private var questions: [Question] = []
    @State private var currentQuestionIndex = 0
    @State private var score = 0
    @State private var shuffledAnswers: [String] = []
    var body: some View {
        VStack {
            ZStack(alignment: .topTrailing) {
                Image("background_top")
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(height: UIScreen.main.bounds.height * 0.35)
                
                VStack {
                    HStack {
                        Image("ic_back")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 10, height: 10)
                            .padding(.leading, 6)
                        
                        Spacer()
                        
                        Button(action: {
                            isNavigateToMenuView = true
                        }) {
                            Text("")
                                .font(.custom("chakrapetchbold", size: 40))
                                .padding(.trailing, 300)
                                .foregroundColor(.white)
                                .padding()
                        }
                    }
                    
                    RoundedRectangle(cornerRadius: 10)
                        .frame(maxWidth: .infinity)
                        .frame(height: 250.0)
                        .foregroundColor(Color.black)
                        .padding()
                        .overlay(
                            VStack {
                                HStack {
                                    Spacer()
                                    Text("\(questionNumber)/5")
                                        .font(.custom("chakrapetchbold", size: 30))
                                        .foregroundColor(.white)
                                        .padding(.horizontal, 30.0)
                                        .padding(.top, 100.0)
                                        .frame(height: 10.0)
                                }
                                HStack{
                                    Spacer()
                                    Text("")
                                        .font(.custom("chakrapetchbold", size: 40))
                                        .padding(.trailing, 300)
                                        .foregroundColor(.white)
                                        .padding()
                                }
                                
                                Spacer()
                                Text("Question")
                                    .font(.custom("chakrapetchbold", size: 20))
                                    .padding(.bottom, 140.0)
                                    .foregroundColor(.white)
                                    .padding()
                            }
                        )
                }
            }
            
            Spacer()
            
            Text("\(timerValue)")
                .font(.custom("chakrapetchbold", size: 40))
                .foregroundColor(.black)
                .padding(.bottom, 20)
            
            VStack(spacing: 12.0) {
                Button(action: {
                    // Action for Choose 1 button
                }) {
                    Text("Choose 1")
                        .font(.custom("chakrapetchbold", size: 16))
                        .foregroundColor(.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.black)
                        .cornerRadius(5)
                }
                Button(action: {
                                    // Action for Choose 2 button
                                }) {
                                    Text("Choose 2")
                                        .font(.custom("chakrapetchbold", size: 16))
                                        .foregroundColor(.white)
                                        .padding()
                                        .frame(maxWidth: .infinity)
                                        .background(Color.black)
                                        .cornerRadius(5)
                                }
                                
                                Button(action: {
                                    // Action for Choose 3 button
                                }) {
                                    Text("Choose 3")
                                        .font(.custom("chakrapetchbold", size: 16))
                                        .foregroundColor(.white)
                                        .padding()
                                        .frame(maxWidth: .infinity)
                                        .background(Color.black)
                                        .cornerRadius(5)
                                }
                                
                                Button(action: {
                                    // Action for Choose 4 button
                                }) {
                                    Text("Choose 4")
                                        .font(.custom("chakrapetchbold", size: 16))
                                        .foregroundColor(.white)
                                        .padding()
                                        .frame(maxWidth: .infinity)
                                        .background(Color.black)
                                        .cornerRadius(5)
                                }
                            }
            .padding(.horizontal, 16.0)
                            .padding(.horizontal, 16)
                            .padding(.bottom, 120)
            
            Button(action: {
                            // Incrementar questionNumber
                            questionNumber += 1
                            timerValue = 30
                            // Verificar si questionNumber es 5
                            if questionNumber > 5 {
                                // Si es 5, navegar a ScoreView
                                isNavigateToScoreView = true
                            }
                        }) {
                Text("Next")
                                    .font(.custom("chakrapetchbold", size: 20))
                                    .foregroundColor(.white)
                                    .padding()
                                    .frame(maxWidth: .infinity)
                                    .background(Color.black)
                                    .cornerRadius(5)
                                    .padding(.horizontal, 16)
                            }
                            .padding(.bottom, 16)
                            
            
                        }
                        .background(Color(.white))
                        .padding(.bottom,50)
                        .edgesIgnoringSafeArea(.all)
                        .navigationBarHidden(true)
                        .fullScreenCover(isPresented: $isNavigateToScoreView) {
                                    ScoreView()
                        .fullScreenCover(isPresented: $isNavigateToMenuView) {
                                            MenuView()
                                        }}
                        
                        .onAppear {
                                    startTimer()
                                }
                                .onDisappear {
                                    stopTimer()
                                }
                            }
    private func setupQuestions() {
            // Define tus preguntas y respuestas aquí
        let questions: [Question] = [
            Question(
                pregunta: "Ordenagailuko teklatuari begiratzen badiozu, ze letra aurkitzen da E eta T letren artean?",
                respuestaCorrecta: "R",
                respuestaIncorrecta1: "D",
                respuestaIncorrecta2: "F",
                respuestaIncorrecta3: "U"
            ),
            Question(
                pregunta: "Zein da munduko kirolik ezagunena?",
                respuestaCorrecta: "Futbola",
                respuestaIncorrecta1: "Saskibaloia",
                respuestaIncorrecta2: "Boleibola",
                respuestaIncorrecta3: "Surfa"
            ),
            Question(
                pregunta: "Kolore horia eta kolore urdina nahasten badituzu, zein kolore lortuko duzu?",
                respuestaCorrecta: "Berdea",
                respuestaIncorrecta1: "Gorria",
                respuestaIncorrecta2: "Laranja",
                respuestaIncorrecta3: "Morea"
            ),
            Question(
                pregunta: "Munduko zein hiritan aurkitzen da Eiffel dorrea?",
                respuestaCorrecta: "Paris",
                respuestaIncorrecta1: "Erroma",
                respuestaIncorrecta2: "Iruñea",
                respuestaIncorrecta3: "Pisa"
            ),
            Question(
                pregunta: "Haragia jaten duten animaliak dira...",
                respuestaCorrecta: "Haragijaleak",
                respuestaIncorrecta1: "Orojaleak",
                respuestaIncorrecta2: "Belarjaleak",
                respuestaIncorrecta3: "Landarejaleak"
            ),
            Question(
                pregunta: "Zein hilabetek izan ditzake 28 edo 29 egun urtearen arabera?",
                respuestaCorrecta: "Otsaila",
                respuestaIncorrecta1: "Martxoa",
                respuestaIncorrecta2: "Urtarrila",
                respuestaIncorrecta3: "Apirila"
            ),
            Question(
                pregunta: "Zenbat minutu ditu ordu batek?",
                respuestaCorrecta: "60",
                respuestaIncorrecta1: "100",
                respuestaIncorrecta2: "50",
                respuestaIncorrecta3: "600"
            ),
            Question(
                pregunta: "Zenbat silaba ditu \"ordenagailua\" hitzak?",
                respuestaCorrecta: "6",
                respuestaIncorrecta1: "4",
                respuestaIncorrecta2: "5",
                respuestaIncorrecta3: "7"
            ),
            Question(
                pregunta: "Lasterketa batean bigarren doanari aurreratzen badiot, ze posizioan geratuko naiz?",
                respuestaCorrecta: "Bigarrena",
                respuestaIncorrecta1: "Lehenengoa",
                respuestaIncorrecta2: "Hirugarrena",
                respuestaIncorrecta3: "Laugarrena"
            ),
            Question(
                pregunta: "Nola esaten da \"osteguna\" ingelesez?",
                respuestaCorrecta: "Thursday",
                respuestaIncorrecta1: "Wednesday",
                respuestaIncorrecta2: "Tuesday",
                respuestaIncorrecta3: "Monday"
            ),
            Question(
                pregunta: "Zenbat alde ditu hexagono batek?",
                respuestaCorrecta: "6",
                respuestaIncorrecta1: "4",
                respuestaIncorrecta2: "7",
                respuestaIncorrecta3: "5"
            ),
            Question(
                pregunta: "Zein da giza gorputzeko hezurrik luzeena?",
                respuestaCorrecta: "Femurra (hanka)",
                respuestaIncorrecta1: "Humeroa (besoa)",
                respuestaIncorrecta2: "Buruhezurra (burua)",
                respuestaIncorrecta3: "Saihetsak (enborra)"
            ),
            Question(
                pregunta: "Zein da Iruñeatik pasatzen den ibaia?",
                respuestaCorrecta: "Arga",
                respuestaIncorrecta1: "Urumea",
                respuestaIncorrecta2: "Irati",
                respuestaIncorrecta3: "Aragoi"
            ),
            Question(
                pregunta: "Nola deitzen da pertsona itsuek erabiltzen duten idazteko eta irakurtzeko metodoa?",
                respuestaCorrecta: "Braille",
                respuestaIncorrecta1: "Marrazkiak",
                respuestaIncorrecta2: "Zeinuak",
                respuestaIncorrecta3: "Trise"
            ),
            Question(
                pregunta: "Gorputzeko zein atal du neurri berdina jaiotzerakoan eta helduak garenean?",
                respuestaCorrecta: "Begiak",
                respuestaIncorrecta1: "Hortzak",
                respuestaIncorrecta2: "Mingaina",
                respuestaIncorrecta3: "Hankako bietz txikia"
            ),
            Question(
                pregunta: "Mozart izan zen...",
                respuestaCorrecta: "Musikaria",
                respuestaIncorrecta1: "Margolaria",
                respuestaIncorrecta2: "Kirolaria",
                respuestaIncorrecta3: "Idazlea"
            ),
            Question(
                pregunta: "Hurrengo hirietatik, zein du itsasoa?",
                respuestaCorrecta: "Donostia",
                respuestaIncorrecta1: "Iruñea",
                respuestaIncorrecta2: "Gasteiz",
                respuestaIncorrecta3: "Maule"
            ),
            Question(
                pregunta: "Zein da munduko mendirik altuena?",
                respuestaCorrecta: "Everest",
                respuestaIncorrecta1: "Annapurna",
                respuestaIncorrecta2: "Hiru Errege Mahaia",
                respuestaIncorrecta3: "Jaizkibel"
            ),
            Question(
                pregunta: "Zer da niretzako nire aitaren anaiaren semea?",
                respuestaCorrecta: "Lehengusua",
                respuestaIncorrecta1: "Osaba",
                respuestaIncorrecta2: "Anaia",
                respuestaIncorrecta3: "Iloba"
            ),
            Question(
                pregunta: "Zein urtaroetan dira egunak gauak baino luzeagoak?",
                respuestaCorrecta: "Uda",
                respuestaIncorrecta1: "Negua",
                respuestaIncorrecta2: "Udazkena",
                respuestaIncorrecta3: "Udaberria"
            ),
            Question(
                pregunta: "Nondik ateratzen da eguzkia?",
                respuestaCorrecta: "Ekialdea",
                respuestaIncorrecta1: "Iparraldea",
                respuestaIncorrecta2: "Hegoaldea",
                respuestaIncorrecta3: "Mendebaldea"
            )
        ]

            
            shuffleAnswers()
        }

    private func shuffleAnswers() {
        // Baraja las opciones de respuesta para la pregunta actual
        questions[currentQuestionIndex].shuffleAnswers()
    }
                            private func startTimer() {
                                timer = Timer.scheduledTimer(withTimeInterval: 1, repeats: true) { _ in
                                    if timerValue > 0 {
                                        timerValue -= 1
                                    } else {
                                        timerValue = 30 // Reiniciar el temporizador
                                        goToNextQuestion()
                                    }
                                }
                            }
                            
                            private func stopTimer() {
                                timer?.invalidate()
                            }
                            
                            private func goToNextQuestion() {
                                questionNumber += 1
            
                                if questionNumber > 5 {
                                    isNavigateToScoreView = true
                                }
                            }
    private func checkAnswer(selectedAnswer: String) {
            let correctAnswer = questions[currentQuestionIndex].respuestaCorrecta
            
            if selectedAnswer == correctAnswer {
                score += 200 // Sumar puntos si la respuesta es correcta
            }
            
            goToNextQuestion() // Avanzar a la siguiente pregunta
        }
    struct Question {
            let pregunta: String
        var respuestaCorrecta: String
        var respuestaIncorrecta1: String
        var respuestaIncorrecta2: String
        var respuestaIncorrecta3: String
        
        mutating func shuffleAnswers() {
                // Baraja las opciones de respuesta
                let answers = [respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3].shuffled()
                respuestaCorrecta = answers[0]
                respuestaIncorrecta1 = answers[1]
                respuestaIncorrecta2 = answers[2]
                respuestaIncorrecta3 = answers[3]
            }
        }                     }
                struct PlayView_Previews: PreviewProvider {
                    static var previews: some View {
                        PlayView()
                    }
                }

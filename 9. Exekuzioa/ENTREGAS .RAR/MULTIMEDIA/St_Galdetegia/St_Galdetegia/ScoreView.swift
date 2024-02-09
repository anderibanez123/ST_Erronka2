//
//  ScoreView.swift
//  St_Galdetegia
//
//  Created by IzarraitzMBP2 on 26/1/24.
//

import SwiftUI

struct ScoreView: View {
    @State private var isMenuViewActive = false
    var body: some View {
        ZStack {
            Color("primary_color").ignoresSafeArea()
            
            VStack {
                Text("PUNTUAZIOA : ")
                    .font(.custom("chakrapetchbold", size: 30))
                    .foregroundColor(.white)
                    .padding(.top, 50)
                
                ScrollView {
                    // Contenido de la tabla de ranking
                    // Puedes agregar aquí el contenido dinámico si es necesario
                }
                .frame(width: 372, height: 477)
                .padding(.top, 12)
                
                Spacer()
                
                Button(action: {
                    isMenuViewActive = true
                }) {
                    Text("HASIERA")
                        .font(.custom("chakrapetchbold", size: 20))
                        .foregroundColor(.white)
                        .padding()
                        .frame(width: 390, height: 68)
                        .background(Color(.red))
                        .cornerRadius(10)
                }
                .padding(.bottom, 20)
                .padding()
                
                
            }
            .padding(.horizontal, 40.0)
            .background(Color(red: 51/255, green: 51/255, blue: 51/255))
            .sheet(isPresented: $isMenuViewActive) {
                MenuView()
            }       
        }
    }
}

struct ScoreView_Previews: PreviewProvider {
    static var previews: some View {
        ScoreView()
    }
}

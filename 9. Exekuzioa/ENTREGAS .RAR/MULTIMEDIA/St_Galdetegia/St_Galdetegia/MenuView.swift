//
//  MenuView.swift
//  St_Galdetegia
//
//  Created by IzarraitzMBP2 on 26/1/24.
//

import SwiftUI

struct MenuView: View {
    @State private var showAlert = false
    @State private var isPlayViewActive = false
    
    var body: some View {
        VStack {
            Text("GALDETEGIA")
                .font(.custom("chakrapetchbold", size: 35))
                .foregroundColor(.white)
                .padding(.top, 200)
            
            Spacer()
            
            VStack(spacing: 16) {
                Button(action: {
                    // Action for Play button
                    isPlayViewActive = true
                }) {
                    Text("Jokatu")
                        .font(.custom("chakrapetchbold", size: 20))
                        .foregroundColor(.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.red)
                        .cornerRadius(5)
                }
                
                Button(action: {
                    // Action for Ranking button
                    showAlert = true
                }) {
                    Text("Ranking")
                        .font(.custom("chakrapetchbold", size: 20))
                        .foregroundColor(.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.red)
                        .cornerRadius(5)
                }
                .alert(isPresented: $showAlert) {
                    Alert(title: Text("Mensaje"), message: Text("¡Todavía no está disponible el ranking!"), dismissButton: .default(Text("OK")))
                }
                
                Button(action: {
                    // Action for Exit button
                    exit(0)
                }) {
                    Text("Sesioa Itxi")
                        .font(.custom("chakrapetchbold", size: 20))
                        .foregroundColor(.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.red)
                        .cornerRadius(5)
                }
            }
            .padding(20)
            
            Spacer()
        }
        .background(Color(red: 51/255, green: 51/255, blue: 51/255))
        .edgesIgnoringSafeArea(.all)
        .sheet(isPresented: $isPlayViewActive) {
            PlayView()
        }
    }
}

struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}

//
//  ContentView.swift
//  St_Galdetegia
//
//  Created by IzarraitzMBP2 on 26/1/24.
//

import SwiftUI

struct ContentView: View {
    
    @State private var dniText = ""
    @State private var passwordText = ""
    @State private var isHelpTextVisible = false
    @State private var isLoginFailed = false
    var body: some View {
        VStack {
            Image("logoaapp2")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 348, height: 180)
            
            TextField("Sartu zure DNIa", text: $dniText)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)
                .background(Color.white)
                .cornerRadius(5)
                .padding(.top, 12)
            
            SecureField("Zure pasahitza sartu", text: $passwordText)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)
                .background(Color.white)
                .cornerRadius(5)
                .padding(.top, 12)
            
            Button(action: {
                
            }) {
                Text("LOGIN")
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.red)
                    .cornerRadius(5)
                    .padding(.top, 24)
            }
            
            Button(action: {
                // Action for register button
            }) {
                Text("REGISTER")
                    .foregroundColor(.white)
                    .padding()
                    .frame(width: 248)
                    .background(Color.red)
                    .cornerRadius(5)
                    .padding(.top, 12)
                    
            }
            
            HStack {
                Spacer()
                
                Button(action: {
                    // Action for help button
                }) {
                    Text("❓")
                        .font(.system(size: 15))
                        .padding(10)
                        .background(Color.gray)
                        .foregroundColor(.white)
                        .clipShape(Circle())
                }
                .padding(.top, 12)
            }
            .padding(.top, 12)
            
            Text("Zure puntuazioa gorde nahi baduzu, kontu bat sortu eta sartu beharko duzu. \n\nEz baduzu daturik gorde nahi, DNI eta PASAHITZA laukietan ezer sartu gabe, login egin. \n\nONDO PASA!")
                .font(.body)
                .foregroundColor(.black)
                .multilineTextAlignment(.center)
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.clear)
                .padding(.top, 12)
                .opacity(isHelpTextVisible ? 1 : 0) // Set visibility based on condition
        }
        .padding(30)
        .background(Color(red: 51/255, green: 51/255, blue: 51/255))
        .edgesIgnoringSafeArea(.all)
    
        
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

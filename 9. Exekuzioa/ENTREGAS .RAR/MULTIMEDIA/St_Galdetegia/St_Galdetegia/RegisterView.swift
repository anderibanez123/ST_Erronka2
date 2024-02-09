//
//  RegisterView.swift
//  St_Galdetegia
//
//  Created by IzarraitzMBP2 on 26/1/24.
//

import SwiftUI

struct RegisterView: View {
    @State private var firstName = ""
    @State private var lastName = ""
    @State private var username = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    @State private var dni = ""
    
    var body: some View {
        VStack {
            Image("ic_back")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 50, height: 50)
                .padding(.top, 20)
            
            Image("logoaapp2")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 348, height: 180)
                .padding(.top, 8)
            
            TextField("Izena", text: $username)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)                .background(Color.white)
                .cornerRadius(5)
                .padding(.vertical, 8)
            
            TextField("Abizena", text: $lastName)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)                .background(Color.white)
                .cornerRadius(5)
                .padding(.vertical, 8)
            
            TextField("DNI", text: $dni)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)                .background(Color.white)
                .cornerRadius(5)
                .padding(.vertical, 8)
            
            SecureField("Pasahitz berria", text: $password)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)        .background(Color.white)
                .cornerRadius(5)
                .padding(.vertical, 8)
            
            SecureField("Baieztatu pasahitza", text: $confirmPassword)
                .padding()
                .border(/*@START_MENU_TOKEN@*/Color.black/*@END_MENU_TOKEN@*/)
                .background(Color.white)
                .cornerRadius(5)
                .padding(.vertical, 8)
            
            Button(action: {
                // Action for register button
            }) {
                Text("Erregistratu")
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.red)
                    .cornerRadius(5)
                    .padding(.top, 8)
            }
            
            Text("Letra")
                .font(.title)
                .foregroundColor(.white)
                .padding(.top, 8)
        }
        .padding(30)
        .background(Color(red: 51/255, green: 51/255, blue: 51/255))
        .edgesIgnoringSafeArea(.all)
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}

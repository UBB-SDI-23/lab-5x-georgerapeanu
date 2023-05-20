import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MessageDTO } from 'src/app/dto/MessageDTO';
import { ChatService } from 'src/app/services/chat.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit{
  chatForm = this.formBuilder.group(
    {
      message: ['', Validators.required],
      nickname: ['', Validators.required]
    }
  );
  messages: MessageDTO[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private chatService: ChatService
  ) {}

  ngOnInit(): void {
    this.chatService.onMessageObservable().subscribe({
      next: (messageDTO) => {
        this.messages.unshift(messageDTO);
      }
    })
  }

  onSubmit() {
    if(!this.chatForm.valid) {
      this.chatForm.markAllAsTouched();
      return ;
    }
    let messageDTO = this.chatForm.value as MessageDTO;
    this.chatService.sendMessage(messageDTO);
    this.chatForm.controls.message.reset();
  }
}

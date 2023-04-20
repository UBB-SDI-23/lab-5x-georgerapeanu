import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Output() pageChangeEvent = new EventEmitter<number>();
  @Input() totalPages: number = 0;
  @Input() currentPage: number = 0;
  @Input() pageNumber: number = 0;

  setPageNumber(page: number) {
    page = Math.max(page, 0);
    page = Math.min(page, this.totalPages - 1);
    this.pageNumber = page;
    this.pageChangeEvent.emit(page);
  }
}

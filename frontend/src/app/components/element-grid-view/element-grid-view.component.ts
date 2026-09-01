import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // ngClass
import { BackendService } from '../../services/backend.service'; // Servicio BackendService
import { ChangeDetectorRef } from '@angular/core';

@Component({
    selector: 'app-element-grid-view',
    standalone: true,
    imports: [CommonModule, ],
    templateUrl: './element-grid-view.component.html',
    styleUrls: ['./element-grid-view.component.css']
})
export class ElementGridViewComponent implements OnInit {
    products: any[] = [];
    currentPage = 1;
    pageSize = 10;

    constructor(private backendService: BackendService, private cdr: ChangeDetectorRef) {}

    ngOnInit(): void {
        setTimeout(() => {
            this.fetchProducts();
        });
    }

    // Call service to obtain product list
    fetchProducts(): void {
        this.backendService.getTableData('products', {}).subscribe({
            next: (data) => {
                this.products = data; 
                this.paginate();  // Init pagination
            },
            error: (err) => {
                console.error('Error fetching products:', err);
            },
        });
    }

    // Realize la pagination
    paginate(): void {
        const start = (this.currentPage - 1) * this.pageSize;
        this.products = this.products.slice(start, start + this.pageSize);
    }

    // Go to next page
    nextPage(): void {
        if (this.currentPage < this.totalPages) {
            this.currentPage++;
            this.paginate();
        }
    }

    // Go to prev page
    prevPage(): void {
        if (this.currentPage > 1) {
            this.currentPage--;
            this.paginate();
        }
    }

    // Calc total pages
    get totalPages(): number {
        return Math.ceil(this.products.length / this.pageSize);
    }
}

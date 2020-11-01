import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeOneComponent} from './components/pages/home-one/home-one.component';
import {AboutComponent} from './components/pages/about/about.component';
import {TeamComponent} from './components/pages/team/team.component';
import {HowItWorksComponent} from './components/pages/how-it-works/how-it-works.component';
import {ContactComponent} from './components/pages/contact/contact.component';
import {CategoriesOneComponent} from './components/pages/categories-one/categories-one.component';
import {CategoriesTwoComponent} from './components/pages/categories-two/categories-two.component';
import {ListingDetailsComponent} from './components/pages/listing-details/listing-details.component';
import {ListingFiveComponent} from './components/pages/listing-five/listing-five.component';
import {ListingFourComponent} from './components/pages/listing-four/listing-four.component';
import {ListingThreeComponent} from './components/pages/listing-three/listing-three.component';
import {ListingTwoComponent} from './components/pages/listing-two/listing-two.component';
import {ListingOneComponent} from './components/pages/listing-one/listing-one.component';
import {ProductListComponent} from './components/pages/product-list/product-list.component';
import {ProductsDetailsComponent} from './components/pages/products-details/products-details.component';
import {CartComponent} from './components/pages/cart/cart.component';
import {CheckoutComponent} from './components/pages/checkout/checkout.component';
import {BlogOneComponent} from './components/pages/blog-one/blog-one.component';
import {BlogTwoComponent} from './components/pages/blog-two/blog-two.component';
import {BlogThreeComponent} from './components/pages/blog-three/blog-three.component';
import {BlogFourComponent} from './components/pages/blog-four/blog-four.component';
import {BlogFiveComponent} from './components/pages/blog-five/blog-five.component';
import {BlogDetailsOneComponent} from './components/pages/blog-details-one/blog-details-one.component';
import {BlogDetailsTwoComponent} from './components/pages/blog-details-two/blog-details-two.component';
import {BlogDetailsThreeComponent} from './components/pages/blog-details-three/blog-details-three.component';
import {FaqComponent} from './components/pages/faq/faq.component';
import {ErrorComponent} from './components/pages/error/error.component';
import {ComingSoonComponent} from './components/pages/coming-soon/coming-soon.component';
import {LoginComponent} from './components/pages/login/login.component';
import {RegisterComponent} from './components/pages/register/register.component';

const routes: Routes = [
    {path: '', component: HomeOneComponent},
    {path: 'about', component: AboutComponent},
    {path: 'team', component: TeamComponent},
    {path: 'how-it-works', component: HowItWorksComponent},
    {path: 'categories-one', component: CategoriesOneComponent},
    {path: 'categories-two', component: CategoriesTwoComponent},
    {path: 'listing-one', component: ListingOneComponent},
    {path: 'listing-two', component: ListingTwoComponent},
    {path: 'listing-three', component: ListingThreeComponent},
    {path: 'listing-four', component: ListingFourComponent},
    {path: 'listing-five', component: ListingFiveComponent},
    {path: 'listing-details', component: ListingDetailsComponent},
    {path: 'blog-one', component: BlogOneComponent},
    {path: 'blog-two', component: BlogTwoComponent},
    {path: 'blog-three', component: BlogThreeComponent},
    {path: 'blog-four', component: BlogFourComponent},
    {path: 'blog-five', component: BlogFiveComponent},
    {path: 'blog-details-one', component: BlogDetailsOneComponent},
    {path: 'blog-details-two', component: BlogDetailsTwoComponent},
    {path: 'blog-details-three', component: BlogDetailsThreeComponent},
    {path: 'product-list', component: ProductListComponent},
    {path: 'products-details', component: ProductsDetailsComponent},
    {path: 'cart', component: CartComponent},
    {path: 'checkout', component: CheckoutComponent},
    {path: 'faqs', component: FaqComponent},
    {path: 'error', component: ErrorComponent},
    {path: 'coming-soon', component: ComingSoonComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'contact', component: ContactComponent},
    {path: '**', component: ErrorComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

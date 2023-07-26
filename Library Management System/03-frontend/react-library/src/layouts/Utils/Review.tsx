import ReviewModel from "../../models/ReviewModel";
import { StarsReview } from "./StarsReview";

export const Review: React.FC<{ review: ReviewModel }> = (props) => {
    
    const date = new Date(props.review.date);

    const longMonth = date.toLocaleString('en-us', { month: 'long' });
    const dateDay = date.getDate();
    const dateYear = date.getFullYear();
    //calculates today's date
    const dateRender = longMonth + ' ' + dateDay + ', ' + dateYear;
    
    return (
        <div>
            <div className='col-sm-8 col-md-8'>
                {/* email */}
                <h5>{props.review.userEmail}</h5>
                <div className='row'>
                    <div className='col'>
                        {/* adds todays date */}
                        {dateRender}
                    </div>
                    {/* componenet for star reviews */}
                    <div className='col'>
                        <StarsReview rating={props.review.rating} size={16} />
                    </div>
                </div>
                <div className='mt-2'>
                    <p>
                        {props.review.reviewDescription}
                    </p>
                </div>
            </div>
            <hr/>
        </div>
    );
}
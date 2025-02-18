//
//  UIView+MJ.m
//  传智微博
//
//  Created by teacher on 14-6-6.
//  Copyright (c) 2014年 itcast. All rights reserved.
//

#import "UIView+MJ.h"

@implementation UIView (MJ)
- (void)setSize:(CGSize)size
{
    CGRect frame = self.frame;
    frame.size = size;
    self.frame = frame;
}

- (CGSize)size
{
    return self.frame.size;
}

- (void)setX:(CGFloat)x
{
    CGRect frame = self.frame;
    frame.origin.x = x;
    self.frame = frame;
}

- (CGFloat)x
{
    return self.frame.origin.x;
}

- (void)setY:(CGFloat)y
{
    CGRect frame = self.frame;
    frame.origin.y = y;
    self.frame = frame;
}

- (CGFloat)y
{
    return self.frame.origin.y;
}

- (void)setWidth:(CGFloat)width
{
    CGRect frame = self.frame;
    frame.size.width = width;
    self.frame = frame;
}

- (CGFloat)width
{
    return self.frame.size.width;
}

- (void)setHeight:(CGFloat)height
{
    CGRect frame = self.frame;
    frame.size.height = height;
    self.frame = frame;
}

- (CGFloat)height
{
    return self.frame.size.height;
}

- (void)setCenterX:(CGFloat)centerX
{
    CGPoint center = self.center;
    center.x = centerX;
    self.center = center;
}

- (CGFloat)centerX
{
    return self.center.x;
}

- (void)setCenterY:(CGFloat)centerY
{
    CGPoint center = self.center;
    center.y = centerY;
    self.center = center;
}

- (CGFloat)centerY
{
    return self.center.y;
}

-(void)settingBorderWithPX:(CGFloat)width colorR:(CGFloat)r g:(CGFloat)g b:(CGFloat)b{
    [self.layer setMasksToBounds:YES];
    [self.layer setBorderWidth:width];
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
    CGColorRef colorref = CGColorCreate(colorSpace,(CGFloat[]){ r/255.0,g/255.0, b/255.0, 1 });
    [self.layer setBorderColor:colorref];
    CGColorRelease(colorref);
    CGColorSpaceRelease(colorSpace);
}



-(void)addTapGestureRecognizerWithTarget:(id)target action:(SEL)action{
    UITapGestureRecognizer* tapGR=[[UITapGestureRecognizer alloc]initWithTarget:target action:action];
    [self setUserInteractionEnabled:YES];
    [self addGestureRecognizer:tapGR];    
}
@end

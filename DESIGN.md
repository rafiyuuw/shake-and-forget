---
name: Shake & Forget
colors:
  surface: '#fff9f0'
  surface-dim: '#dfd9d1'
  surface-bright: '#fff9f0'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f9f3ea'
  surface-container: '#f3ede4'
  surface-container-high: '#ede7df'
  surface-container-highest: '#e7e2d9'
  on-surface: '#1d1b16'
  on-surface-variant: '#484554'
  inverse-surface: '#32302a'
  inverse-on-surface: '#f6f0e7'
  outline: '#797586'
  outline-variant: '#cac3d7'
  surface-tint: '#6540d5'
  primary: '#5e38ce'
  on-primary: '#ffffff'
  primary-container: '#7755e8'
  on-primary-container: '#f9f3ff'
  inverse-primary: '#ccbeff'
  secondary: '#aa304e'
  on-secondary: '#ffffff'
  secondary-container: '#fd6f8b'
  on-secondary-container: '#700027'
  tertiary: '#705400'
  on-tertiary: '#ffffff'
  tertiary-container: '#8e6b00'
  on-tertiary-container: '#fff4e4'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#e7deff'
  primary-fixed-dim: '#ccbeff'
  on-primary-fixed: '#1f0060'
  on-primary-fixed-variant: '#4c20bc'
  secondary-fixed: '#ffd9dd'
  secondary-fixed-dim: '#ffb2bc'
  on-secondary-fixed: '#400013'
  on-secondary-fixed-variant: '#8a1638'
  tertiary-fixed: '#ffdf9b'
  tertiary-fixed-dim: '#edc157'
  on-tertiary-fixed: '#251a00'
  on-tertiary-fixed-variant: '#5b4300'
  background: '#fff9f0'
  on-background: '#1d1b16'
  surface-variant: '#e7e2d9'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '800'
    lineHeight: 48px
    letterSpacing: -0.03em
  display-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.01em
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '700'
    lineHeight: 24px
    letterSpacing: 0em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
    letterSpacing: 0.01em
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 15px
    fontWeight: '700'
    lineHeight: 20px
    letterSpacing: 0.02em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.04em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 11px
    fontWeight: '800'
    lineHeight: 14px
    letterSpacing: 0.06em
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  gutter: 1rem
  margin: 1.25rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2.25rem
---

## Brand & Style

This design system embodies pure kinetic spontaneity, relief, and cheerful decisiveness. Built for an Android-first mobile experience, the visual language addresses "analysis paralysis" with warmth, lighthearted whimsy, and tactile delight.

The style is **Playful Modern Casual** underpinned by **Material 3 tactile physics**. It treats UI components as bouncy, touch-responsive toy-like blocks:
- **Atmosphere:** Uplifting, joyful, and frictionless. It avoids sterile productivity patterns in favor of tactile confidence and kinetic satisfaction.
- **Physicality:** Thick, pillowy radiuses, extruded button depths that compress on tap, and rich pastel-saturated containers resting on a warm, milk-cream canvas.
- **Tone:** Encouraging, witty, and dismissive of overthinking ("Stop overthinking. Let it shake."). Micro-copy is punchy, casual, and energetic.

## Colors

The palette employs vibrant, high-energy pastels grounded by deep indigo typography and a soothing, warm cream base.

- **Primary (`#7755E8` - Electric Grape):** Drives primary actions, shake trigger states, key badges, and interactive navigation selections.
- **Secondary (`#FF718D` - Coral Pink):** Powers accent decisions, destructive or playful dismissals, active alerts, and dynamic tag highlights.
- **Tertiary (`#FFD166` - Sunny Yellow):** Used for celebration moments, prize stars, secondary badges, and highlighted decision cards.
- **Supporting Accents:**
  - **Mint Green (`#69D6B2`):** Affirmative outcomes, successful rolls, toggles, and completed decisions.
  - **Sky Blue (`#70C5F5`):** Secondary cards, alternate choices, ambient pill backgrounds, and info chips.
- **Neutral Canvas (`#FFF9F0` - Milk Cream):** Replaces harsh stark whites for overall screen canvases, giving the interface an organic, friendly softness.
- **Surface Elevation (`#FFFFFF` - Pure White):** Used for elevated decision cards, dialogs, and nested input surfaces.
- **Text & Structure:**
  - **Primary Ink (`#29243A`):** High-contrast, plum-tinted charcoal for maximum legibility without sterile black tones.
  - **Secondary Ink (`#817B91`):** Subtitle copy, metadata, unselected nav states, and disabled iconography.

## Typography

The typography leverages **Plus Jakarta Sans** across all hierarchies to ensure high readability paired with soft, circular terminals.

- **Headlines & Titles:** Rendered in Bold (700) and Extra Bold (800) with slight negative tracking to give a tight, sticker-like typographic impact for shake results and celebration titles.
- **Body:** Uses Medium (500) weight rather than regular weight, ensuring text holds weight and presence against saturated background cards and pastels.
- **Labels & Badges:** Uppercase or small title-case labels use Bold/Extra-Bold tracking to function like durable toy labels and navigation markers.

## Layout & Spacing

Designed strictly around ergonomic hand zones and Android safe areas:
- **Canvas Margins:** Screen boundaries stick to `1.25rem` (20px) on standard devices, providing ample breathing room around chunky card components.
- **Component Padding:** Internal card padding is generous (`space-lg` / 24px) to retain an airy, inviting, pillowy structure.
- **Stack Gaps:** Sequential list options and input fields use `space-md` (16px) for distinct separation between interactive blocks.
- **Thumb Zone:** Primary execution elements (such as the main "Shake Now" button or kinetic jar) are permanently docked or centered within the lower two-thirds of the viewport.

## Elevation & Depth

Visual hierarchy uses physical, tactile elevation rather than generic blurry drop shadows:
- **Hard-Drop Tactile Shadows:** Elevated components use crisp, semi-offset shadows tinted with deep primary ink (`rgba(41, 36, 58, 0.12)`) offset 4px vertically.
- **Tactile Pressed State:** Interactive buttons feature a real-time dimensional drop: default has an offset of `0px 4px 0px rgba(41, 36, 58, 0.18)`; during press/tap, the element translates down `3px` and the offset shrinks to `0px 1px 0px`, simulating a physical mechanical push.
- **Floating Result Overlays:** Modal decision cards use a dual shadow: a wide ambient soft blur (`0px 12px 32px rgba(119, 85, 232, 0.16)`) combined with a grounding rim outline (`2px solid rgba(41, 36, 58, 0.06)`).

## Shapes

The shape system is intentionally extra-rounded and bulbous:
- **Level 3 (Pill-shaped):** Interactive controls, badges, chips, search inputs, and primary buttons use full pill geometry (`9999px` or minimum `1rem` up to `2rem`).
- **Cards & Modals:** Use soft, pebble-style curves (`rounded-3xl` / 24px-28px radius) to evoke rounded physical tokens or handheld game cartridges.
- **Borders:** Subtle structural borders (`2px solid rgba(41, 36, 58, 0.08)`) encase pastel surfaces, preventing light colors from washing out against the cream background.

## Components

### Buttons
- **Primary Kinetic Button ("Shake"):** Full-width or giant circular container. `#7755E8` background, `#FFFFFF` bold text, 3D extruded rim with a 4px bottom drop. Haptic feedback triggers on press alongside a slight scale-down animation (`scale(0.97)`).
- **Secondary Action Buttons:** Pill-shaped, tinted in `#FF718D` (Coral) or `#FFD166` (Yellow) with `#29243A` text for actions like "Roll Again" or "Add Choice".

### Cards & Decision Tiles
- **Vibrant Choice Cards:** Clean `#FFFFFF` surface or alternate tinted backgrounds (`#70C5F5`, `#69D6B2`) with rounded-3xl corners (24px). Padded with `1.25rem`. Includes an optional tactile badge in the top-right corner.
- **Result Reveal Card:** Bold primary frame, micro-confetti styling, thick cream border, containing huge typography (`headline-lg`) delivering the final unarguable verdict.

### Chips & Badges
- **Status Badges:** Small pill capsules with `label-sm` text. Pastel backings (`#FFD166`, `#69D6B2`, `#FF718D`) with crisp `#29243A` typography.
- **Removable Item Chips:** Pill-shaped inputs with soft inner padding, leading bullet icon, and an interactive cross ("✕") icon in a circular `#FFFFFF` container.

### Inputs & Option Creators
- **Text Input Fields:** `#FFFFFF` background with `2px solid #817B91` (unfocused) shifting to `2px solid #7755E8` (focused). Height is 56px with full pill roundedness, inset with playful placeholder copy (e.g., "Tacos? Pizza? World domination?").
- **Add Trigger:** Attached circle button with a plush `+` icon that bounces slightly when valid characters are typed.

### Checkboxes & Toggle Switches
- **Checkboxes:** Rounded squares (radius 10px) with 2px borders in `#29243A`. Selected state floods with `#69D6B2` and reveals a chunky white checkmark.
- **Toggles:** Oversized pill track with a tactile knob that shifts smoothly, flashing `#69D6B2` when enabled.

### Android Bottom Navigation Bar
- **Material 3 Custom Bar:** Docked to bottom with top rounded corners (24px). Background is pure white `#FFFFFF` resting over the `#FFF9F0` canvas.
- **Active Indicators:** Pill capsule in `#7755E8` with `#FFFFFF` iconography for active tabs, accompanied by playful micro-dots beneath. Inactive icons remain in `#817B91`.
- **Center "Shake" FAB:** Centered floating circular action button breaking the top edge of the navigation bar, pulsing subtly when choices are ready to be shaken.
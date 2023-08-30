package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.CursePower;
import theGamblignant.powers.LuckPower;
import theGamblignant.powers.VimPower;
import theGamblignant.relics.BionicBoundbeastRelic;

import static theGamblignant.VriskaMod.makeCardPath;

public class FlipKick extends AbstractVriskaCard {

    // TEXT DECLARATION

    public static final String ID = VriskaMod.makeID(FlipKick.class.getSimpleName());
    public static final String IMG = makeCardPath("flipkick.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    private static final int DAMAGE = 8;

    // /STAT DECLARATION/


    public FlipKick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.doesRoll = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        int cantriproll = roll(1,2,'a');
        if (cantriproll == 2) {
            this.addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            this.addToBot(new GainEnergyAction(1));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        triggerOnGlowCheck();
    }

    public void triggerOnGlowCheck() { //if the player has effects that would guarantee the roll to succeed, make the card glow yellow
        AbstractPlayer p = AbstractDungeon.player;
        int luck = 0;
        int vim = 0;
        int curse = 0;

        if (p.hasPower(LuckPower.POWER_ID)) {
            luck = p.getPower(LuckPower.POWER_ID).amount;
        }
        if (p.hasPower(VimPower.POWER_ID)) {
            vim = p.getPower(VimPower.POWER_ID).amount;
        }
        if (p.hasPower(CursePower.POWER_ID)) {
            curse = p.getPower(CursePower.POWER_ID).amount;
        }
        if (p.hasRelic(BionicBoundbeastRelic.ID)) {luck++;}

        if (luck + vim - curse >= 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}
